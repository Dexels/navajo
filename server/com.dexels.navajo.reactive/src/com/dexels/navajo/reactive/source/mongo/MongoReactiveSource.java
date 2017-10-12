package com.dexels.navajo.reactive.source.mongo;

import static com.dexels.navajo.document.Property.BINARY_PROPERTY;
import static com.dexels.navajo.document.Property.BOOLEAN_PROPERTY;
import static com.dexels.navajo.document.Property.CLOCKTIME_PROPERTY;
import static com.dexels.navajo.document.Property.DATE_PATTERN_PROPERTY;
import static com.dexels.navajo.document.Property.DATE_PROPERTY;
import static com.dexels.navajo.document.Property.EXPRESSION_PROPERTY;
import static com.dexels.navajo.document.Property.FLOAT_PROPERTY;
import static com.dexels.navajo.document.Property.INTEGER_PROPERTY;
import static com.dexels.navajo.document.Property.LIST_PROPERTY;
import static com.dexels.navajo.document.Property.LONG_PROPERTY;
import static com.dexels.navajo.document.Property.MEMO_PROPERTY;
import static com.dexels.navajo.document.Property.MONEY_PROPERTY;
import static com.dexels.navajo.document.Property.PASSWORD_PROPERTY;
import static com.dexels.navajo.document.Property.PERCENTAGE_PROPERTY;
import static com.dexels.navajo.document.Property.POINTS_PROPERTY;
import static com.dexels.navajo.document.Property.STOPWATCHTIME_PROPERTY;
import static com.dexels.navajo.document.Property.STRING_PROPERTY;
import static com.dexels.navajo.document.Property.TIMESTAMP_PROPERTY;
import static com.dexels.navajo.document.Property.TIPI_PROPERTY;
import static com.dexels.navajo.document.Property.URL_PROPERTY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.BsonArray;
import org.bson.BsonBinary;
import org.bson.BsonBoolean;
import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonNull;
import org.bson.BsonString;
import org.bson.BsonTimestamp;
import org.bson.BsonValue;
import org.bson.Document;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.mongo.stream.MongoSupplier;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.api.ReplicationMessage;
import com.mongodb.reactivestreams.client.MongoCollection;

import io.reactivex.Flowable;
public class MongoReactiveSource implements ReactiveSource {

	
	private final static Logger logger = LoggerFactory.getLogger(MongoReactiveSource.class);

	
//	private final Map<String, BiFunction<StreamScriptContext,Optional<ReplicationMessage>, Operand>> namedParameters;
//	private final List<BiFunction<StreamScriptContext,Optional<ReplicationMessage>, Operand>> unnamedParameters;
    protected static final String PLACEHOLDER = "?";
    private final ReactiveParameters parameters;


	private final List<ReactiveTransformer> transformers;


	public MongoReactiveSource(
			ReactiveParameters parameters, List<ReactiveTransformer> transformers) {
		this.parameters = parameters;
		this.transformers = transformers;
	}

	// aggregate find
	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ReplicationMessage> current) {
		try {
			Map<String,Operand> pp = parameters.resolveNamed(context, current);
			
			String name = (String) pp.get("resource").value;
			String collection = (String) pp.get("collection").value;
			String query = (String) pp.get("query").value;
			String operation = (String) pp.get("operation").value;
			Optional<Operand> projectionString = Optional.ofNullable(pp.get("projection"));
			Optional<String> projection = projectionString.isPresent() ? Optional.ofNullable((String)projectionString.get().value) : Optional.empty();
			BsonValue doc = query.startsWith("[")?BsonArray.parse(query):BsonDocument.parse(query);
			java.util.function.Function<MongoCollection<BsonDocument>, Publisher<BsonDocument>> resolvedOperation = null;
			traverse(parameters.resolveUnnamed(context, current),doc);

			switch (operation) {
			case "find":
				if (projection.isPresent()) {
					resolvedOperation = d->d.find((BsonDocument)doc,BsonDocument.class).projection(Document.parse(projection.get()));
				} else {
					resolvedOperation = d->d.find((BsonDocument)doc,BsonDocument.class);
				}
				break;
			case "aggregate":
				BsonArray arr = (BsonArray)doc;
				List<BsonDocument> vc = arr.stream().map(e->(BsonDocument)e).collect(Collectors.toList());
				resolvedOperation = d->d.aggregate(vc,BsonDocument.class);
				break;
			default:
				throw new UnsupportedOperationException("Unknown mongo operation: "+operation);
			}
			Flowable<DataItem> flow = MongoSupplier.getInstance().query(context, name, collection,
					resolvedOperation).map(e->DataItem.of(e));
			
			for (ReactiveTransformer reactiveTransformer : transformers) {
				flow = flow.compose(reactiveTransformer.execute(context, current));
			}
			return flow;
		} catch (Exception e) {
			return Flowable.error(e);
		}
	}

	protected void traverse(List<Operand> params, BsonValue o) {
        traverseSub(params, o);       
    }
	

	@SuppressWarnings("unchecked")
	protected void traverseSub(List<Operand> params, BsonValue vl) {
        if (vl == null) {
            return;
        }
        if(vl instanceof BsonArray) {
        		BsonArray ba = (BsonArray)vl;
        		for (BsonValue bsonValue : ba) {
        			traverseSub(params, bsonValue);
			}
        		return;
        }
        if(! (vl instanceof BsonDocument)) {
        		throw new UnsupportedOperationException("Traversal only works in arrays or documents");
        }
        BsonDocument o = (BsonDocument)vl;
        List<String> copySet = new ArrayList<String>(o.keySet());
        for (String ss : copySet) {
            BsonValue value = o.get(ss);
//            if(value!=null && value.isString()) {
//            		BsonString s = value.asString();
//            		s.getValue()
//            }
            if (value!=null && value.isString() && value.asString().getValue().equals(PLACEHOLDER)) {
                if (params.isEmpty()) {
                    throw new IllegalArgumentException("No params left for " +  ss);
                }
                Operand replace = params.remove(0);
                BsonValue bv = createBsonFromOperand(replace);
                o.put(ss,bv);
                logger.debug("Set parameter {} at {}", replace, ss);
                
            } else if (value instanceof List) {
                List<Object> l = (List<Object>) value;
                for (int i = 0; i < l.size(); i++) {
                    Object element = l.get(i);
                    if (element instanceof BsonDocument) {
                        traverseSub(params, (BsonDocument) element);
                    } else if (PLACEHOLDER.equals(element)) {
                        Object replace = params.remove(0);
                        l.set(i, replace);
                        logger.debug("Set parameter {} at pos {} in {}", replace, i, l);
                    }
                }
            } else if (value instanceof BsonDocument) {
                traverse(params, (BsonDocument) value);
            } else {
                // non-string primitive value, eg integer, boolean, etc. Not interested in those
            }
        }
    }

	private BsonValue createBsonFromOperand(Operand replace) {
		if(replace.value==null) {
			return new BsonNull();
		}
		switch(replace.type) {
		case STRING_PROPERTY:
		case MEMO_PROPERTY:
		case PASSWORD_PROPERTY:
			return new BsonString((String) replace.value);
		case BOOLEAN_PROPERTY:
			return new BsonBoolean((Boolean)replace.value);
		case FLOAT_PROPERTY:
		case MONEY_PROPERTY:
		case PERCENTAGE_PROPERTY:
			return new BsonDouble((Double)replace.value);
		case INTEGER_PROPERTY:
			return new BsonInt32((Integer)replace.value);
		case LONG_PROPERTY:
			return new BsonInt64((Long)replace.value);
		case TIMESTAMP_PROPERTY:
			return new BsonTimestamp(((Date)replace.value).getTime());
		case DATE_PROPERTY: // trunc time?
			return new BsonDateTime(((Date)replace.value).getTime());
		case CLOCKTIME_PROPERTY: // trunc time?
			return new BsonDateTime(((ClockTime)replace.value).dateValue().getTime());
		case BINARY_PROPERTY:
			Binary b = (Binary) replace.value;
			return new BsonBinary(b.getData());
		case STOPWATCHTIME_PROPERTY:
		case POINTS_PROPERTY:
		case URL_PROPERTY:
		case EXPRESSION_PROPERTY:
		case DATE_PATTERN_PROPERTY:
		case TIPI_PROPERTY:
		case LIST_PROPERTY:
		default:
			throw new UnsupportedOperationException("Can convert this type: "+replace.type+" to BSON. Contributions welcome");
		}
	}
}
