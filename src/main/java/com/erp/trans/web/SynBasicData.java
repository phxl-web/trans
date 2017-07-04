package com.erp.trans.web;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.trans.common.constant.CustomConst;
import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.util.JSONUtils;
import com.erp.trans.common.util.LocalAssertUtils;
import com.erp.trans.common.util.WebConnect;
import com.erp.trans.entity.Brand;
import com.erp.trans.entity.Series;
import com.erp.trans.service.SynBasicDataService;
import com.erp.trans.web.dto.SeriesDto;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("staticData")
public class SynBasicData {
	public final static Logger logger = LoggerFactory.getLogger(SynBasicData.class);
    private String getSerialUrl = "http://www.autohome.com.cn//ashx/AjaxIndexCarFind.ashx?type=3&value={0}";

	@Autowired
	SynBasicDataService synBasicDataService;
	/**
	 * 导入汽车品牌(Json文件)
	 * */
	@RequestMapping("importBrand")
	@ResponseBody
	public void importTdBrandJson() throws Exception {
			long startTime = System.currentTimeMillis();
	        WebConnect webConnect = new WebConnect();
	        webConnect.initWebClient();
			try{
				ObjectMapper oMapper = JSONUtils.getObjectMapper();
				List<Brand> brands = oMapper.readValue(new FileReader("/Users/eva/Desktop/branditems.txt"), JSONUtils.getCollectionType(ArrayList.class, Brand.class));
				for(Brand brand:brands){
			        String url = MessageFormat.format(getSerialUrl, brand.getBrandId());
			        String responseString = webConnect.executeHttpGet(url);
			        JSONObject json = JSONObject.fromObject(responseString);
			        JSONObject jsonResult = JSONObject.fromObject(json.get("result"));
			        JavaType javaType = JSONUtils.getCollectionType(ArrayList.class, SeriesDto.class);
			        List<SeriesDto> seriesDtos = oMapper.readValue(jsonResult.getString("factoryitems"), javaType);
			        for(SeriesDto seriesDto:seriesDtos){
			        	Brand brandSeries = new Brand();
			        	brandSeries.setBrandId(seriesDto.getId());
			        	brandSeries.setBrandName(seriesDto.getName());
			        	brandSeries.setBrandFirstLetter(seriesDto.getFirstletter());
			        	brandSeries.setBrandParent(brand.getBrandId());
			        	brandSeries.setModifyDate(new Date());
			        	synBasicDataService.insertInfo(brandSeries);
			        	List<SeriesDto> seriesDtoTemps = seriesDto.getSeriesitems();
			        	for(SeriesDto seriesDtoTemp:seriesDtoTemps){
				        	Series series = new Series();
				        	series.setSeriesId(seriesDtoTemp.getId());
				        	series.setSeriesName(seriesDtoTemp.getName());
				        	series.setSeriesFirstLetter(seriesDtoTemp.getFirstletter());
				        	series.setBrandId(seriesDto.getId());
				        	series.setSeriesOrder(seriesDtoTemp.getSeriesorder());
				        	series.setModifyDate(new Date());
				        	synBasicDataService.insertInfo(series);
			        	}
			        }
					//synBasicDataService.insertInfo(brand);
				}
//				JsonElement pje = jsonParser.parse(new FileReader("/Users/eva/Desktop/branditems.txt"));		
//				JsonArray pidArray = pje.getAsJsonArray();
//				for (JsonElement jn : pidArray) {	
//					JsonObject jo = jn.getAsJsonObject();
//					System.out.println(jo.get("id")+"   "+jo.get("name").getAsString()+"   "+"   "+jo.get("bfirstletter").getAsString());
//				}
			}catch (JsonIOException e) {
	            e.printStackTrace();
	        } catch (JsonSyntaxException e) {
	            e.printStackTrace();
	        } 
	        catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
		//TASK 可以做一些校验。
			logger.info("基础数据->导入汽车品牌->导入成功: 使用时间{}（秒）",
					BigDecimal.valueOf((System.currentTimeMillis() - startTime)/1000d).setScale(3));
		}
	
	/**
	 * 【基础数据】查询系统中所有的普通机构，用来做承运商查询的下拉框
	 * @param searchParams
	 */
	@ResponseBody
	@RequestMapping(value = "/searchOrgForSelect")
	public List<Map<String,Object>> searchOrgForSelect(String searchParams, HttpSession session) {
		Pager pager = new Pager(false);
		
		pager.addQueryParam("searchParams", searchParams);
		pager.addQueryParam("orgType", CustomConst.OrgType.ORGNORMAL);

		return synBasicDataService.searchOrgForSelect(pager);
	}
	/**
	 * 【基础数据】查询系统中所有的城市，用于起运地和目的地的选择
	 * @param searchParams
	 */
	@ResponseBody
	@RequestMapping(value = "/searchLocationsForSelect")
	public List<Map<String,Object>> searchLocationsForSelect(String searchParams, HttpSession session) {
		Pager pager = new Pager(false);
		
		pager.addQueryParam("searchParams", searchParams);

		return synBasicDataService.searchLocationsForSelect(pager);
	}
	
	@ResponseBody
	@RequestMapping(value = "/carFindForSelect")
	public List<Map<String,Object>> carFindForSelect(String searchParams, 
			String type,
			String pid,
			HttpSession session) throws ValidationException {
		LocalAssertUtils.notBlank(type, "请确定要查询的类型，品牌或车型");
		Pager pager = new Pager(false);
		
		pager.addQueryParam("searchParams", searchParams);
		pager.addQueryParam("type", type);
		pager.addQueryParam("pid", pid);

		return synBasicDataService.carFindForSelect(pager);
	}

	public static void main(String[] args) throws Exception {
//		importTdBrandJson();
	}
}
