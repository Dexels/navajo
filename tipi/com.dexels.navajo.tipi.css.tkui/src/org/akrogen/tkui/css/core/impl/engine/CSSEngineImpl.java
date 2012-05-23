package org.akrogen.tkui.css.core.impl.engine;

import org.akrogen.tkui.css.core.dom.ExtendedDocumentCSS;
import org.akrogen.tkui.css.core.dom.parsers.CSSParser;
import org.akrogen.tkui.css.core.dom.parsers.CSSParserFactory;
import org.akrogen.tkui.css.core.dom.parsers.ICSSParserFactory;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler;
import org.akrogen.tkui.css.core.dom.properties.converters.CSSValueBooleanConverterImpl;
import org.akrogen.tkui.css.core.dom.properties.providers.CSSPropertyHandlerLazyProviderImpl;
import org.akrogen.tkui.css.core.dom.properties.providers.CSSPropertyHandlerSimpleProviderImpl;
import org.akrogen.tkui.css.core.impl.sac.CSSConditionFactoryImpl;
import org.akrogen.tkui.css.core.impl.sac.CSSSelectorFactoryImpl;
import org.w3c.css.sac.ConditionFactory;

public class CSSEngineImpl extends AbstractCSSEngine {

	public static final ConditionFactory CONDITIONFACTORY_INSTANCE = new CSSConditionFactoryImpl(
			null, "class", null, "id");

	private CSSPropertyHandlerSimpleProviderImpl handlerProvider = null;

	private CSSPropertyHandlerLazyProviderImpl lazyHandlerProvider = null;

	public CSSEngineImpl() {
		super();
		// Register SWT Boolean CSSValue Converter
		super.registerCSSValueConverter(CSSValueBooleanConverterImpl.INSTANCE);
	}

	public CSSEngineImpl(ExtendedDocumentCSS documentCSS) {
		super(documentCSS);
		// Register SWT Boolean CSSValue Converter
		super.registerCSSValueConverter(CSSValueBooleanConverterImpl.INSTANCE);
	}

	public CSSParser makeCSSParser() {
		// Create CSS Parser
		ICSSParserFactory factory = CSSParserFactory.newInstance();
		CSSParser parser = factory.makeCSSParser();

		// Register Batik CSS Selector factory.
		parser.setSelectorFactory(CSSSelectorFactoryImpl.INSTANCE);

		// Register Custom CSS Condition factory.
		parser.setConditionFactory(CONDITIONFACTORY_INSTANCE);

		return parser;
	}

	public void registerCSSPropertyHandler(Class cl, ICSSPropertyHandler handler) {
		initHandlerProviderIfNeed();
		handlerProvider.registerCSSPropertyHandler(cl, handler);
	}

	private void initHandlerProviderIfNeed() {
		if (handlerProvider == null) {
			handlerProvider = new CSSPropertyHandlerSimpleProviderImpl();
			super.registerCSSPropertyHandlerProvider(handlerProvider);
		}
	}

	public void registerCSSProperty(String propertyName,
			Class propertyHandlerClass) {
		initHandlerProviderIfNeed();
		handlerProvider.registerCSSProperty(propertyName, propertyHandlerClass);
	}

	private void initLazyHandlerProviderIfNeed() {
		if (lazyHandlerProvider == null) {
			lazyHandlerProvider = new CSSPropertyHandlerLazyProviderImpl();
			super.registerCSSPropertyHandlerProvider(lazyHandlerProvider);
		}
	}

	public void registerPackage(String packageName) {
		initLazyHandlerProviderIfNeed();
		lazyHandlerProvider.registerPackage(packageName);
	}

}
