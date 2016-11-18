package org.ibas.credit.util;

import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

public class DateJsonValueProcessor   implements JsonBeanProcessor {
	public JSONObject processBean(Object bean, JsonConfig arg1) {
		JSONObject jsonObject = null;
		if (bean instanceof java.sql.Date) {
			bean = new Date(((java.sql.Date) bean).getTime());
		}
		if (bean instanceof java.sql.Timestamp) {
			bean = new Date(((java.sql.Timestamp) bean).getTime());
		}
		if (bean instanceof Date) {
			jsonObject = new JSONObject();
			jsonObject.element("time", ((Date) bean).getTime());
		} else {
			jsonObject = new JSONObject(true);
		}
		return jsonObject;
	}

}
