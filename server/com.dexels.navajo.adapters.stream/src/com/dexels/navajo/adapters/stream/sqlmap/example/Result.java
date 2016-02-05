package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoFactory;
import com.github.davidmoten.rx.jdbc.ResultSetMapper;

import rx.functions.Func2;

public class Result implements ResultSetMapper<Message> {

	private String name;
	private Func2<ResultSet,Message,Message> action;

	public Result(String name, Func2<ResultSet,Message,Message> action) {
		this.name = name;
		this.action = action;
	}

	@Override
	public Message call(ResultSet r) throws SQLException {
		Message mm = NavajoFactory.getInstance().createMessage(null, name, Message.MSG_TYPE_ARRAY_ELEMENT);
		return this.action.call(r,mm);
	}


	
}
