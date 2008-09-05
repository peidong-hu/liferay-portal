/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="Message.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 *
 */
public class Message implements Serializable {

	public Object get(String key) {
		if (_values == null) {
			return null;
		}
		else {
			return _values.get(key);
		}
	}

	public boolean getBoolean(String key) {
		boolean value;

		Object object = get(key);

		if (object instanceof Boolean) {
			value = ((Boolean)object).booleanValue();
		}
		else {
			value = GetterUtil.getBoolean((String)object);
		}

		return value;
	}

	public double getDouble(String key) {
		double value;

		Object object = get(key);

		if (object instanceof Number) {
			value = ((Number)object).doubleValue();
		}
		else {
			value = GetterUtil.getDouble((String)object);
		}

		return value;
	}

	public int getInteger(String key) {
		int value;

		Object object = get(key);

		if (object instanceof Number) {
			value = ((Number)object).intValue();
		}
		else {
			value = GetterUtil.getInteger((String)object);
		}

		return value;
	}

	public long getLong(String key) {
		long value;

		Object object = get(key);

		if (object instanceof Number) {
			value = ((Number)object).longValue();
		}
		else {
			value = GetterUtil.getLong((String)object);
		}

		return value;
	}

	public Object getPayload() {
		return _payload;
	}

	public String getResponseDestination() {
		return _responseDestination;
	}

	public String getString(String key) {
		return GetterUtil.getString(String.valueOf(get(key)));
	}

	public void put(String key, Object value) {
		if (_values == null) {
			 _values = new HashMap<String, Object>();
		}

		_values.put(key, value);
	}

	public void setPayload(Object payload) {
		_payload = payload;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{responseDestination=");
		sb.append(_responseDestination);
		sb.append(", ");
		sb.append("responseId=");
		sb.append(_responseId);
		sb.append(", ");
		sb.append("payload=");
		sb.append(_payload);
		sb.append(", ");
		sb.append("values=");
		sb.append(_values);
		sb.append("}");

		return sb.toString();
	}

	protected String getDestination() {
		return _destination;
	}

	protected String getResponseId() {
		return _responseId;
	}

	protected void setDestination(String destination) {
		_destination = destination;
	}

	protected void setResponseDestination(String responseDestination) {
		_responseDestination = responseDestination;
	}

	protected void setResponseId(String responseId) {
		_responseId = responseId;
	}

	private String _destination;
	private String _responseDestination;
	private String _responseId;
	private Object _payload;
	private Map<String, Object> _values;

}