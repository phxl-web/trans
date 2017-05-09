package com.erp.trans.common.converter;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 文本串按票房切割成串列表 <br/>
 * @date: 2016年4月27日 下午3:36:18 <br/>
 *
 * @version 1.0
 * @since JDK 1.6
 */
public class TokenizeSplitToListConvertor implements Convertor {
	/**分隔符*/
	final String CONFIG_LOCATION_STRING_DELIMITERS = ",; \t\n";

	@Override
	public List<String> convert(String source) {
		if(!StringUtils.isEmpty(source)) {
			String[] array=StringUtils.tokenizeToStringArray(source, CONFIG_LOCATION_STRING_DELIMITERS);
			return array==null ? null : CollectionUtils.arrayToList(array);
		}
		return null;
	}
}
