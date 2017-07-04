package com.erp.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.trans.common.constant.CustomConst;
import com.erp.trans.common.constant.CustomConst.ConsignFstate;
import com.erp.trans.common.entity.Pager;
import com.erp.trans.common.exception.ValidationException;
import com.erp.trans.common.service.impl.BaseService;
import com.erp.trans.common.util.IdentifieUtil;
import com.erp.trans.common.util.LocalCollectionUtils;
import com.erp.trans.common.util.LocalCollectionUtils.Processer;
import com.erp.trans.dao.ChargeInfoMapper;
import com.erp.trans.dao.ConsignDetailMapper;
import com.erp.trans.dao.ConsignMapper;
import com.erp.trans.dao.DespatchPlanMapper;
import com.erp.trans.dao.DesplanConnoMapper;
import com.erp.trans.entity.Consign;
import com.erp.trans.entity.ConsignDetail;
import com.erp.trans.entity.DespatchPlan;
import com.erp.trans.entity.UserInfo;
import com.erp.trans.service.TransManagementService;
import com.erp.trans.web.dto.ConsignDto;

@Service
public class TransManagementServiceImpl  extends BaseService implements TransManagementService {

	@Autowired
	ConsignMapper consignMapper;
	@Autowired
	ConsignDetailMapper consignDetailMapper;
	@Autowired
	DesplanConnoMapper desplanConnoMapper;
	@Autowired
	DespatchPlanMapper despatchPlanMapper;
	@Autowired
	ChargeInfoMapper chargeInfoMapper;
	
	@Override
	public List<UserInfo> findOrgUserList(Pager pager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void importConsigns(List<ConsignDto> entityList, UserInfo userInfo) throws Exception {
		
		//将导入的运单根据运单号分成主表，同一个运单号里不同的车型成为明细
		List<ConsignDto> consigns = new ArrayList<ConsignDto>();
		List<ConsignDto> consignDetails = new ArrayList<ConsignDto>();
		Map<String, List<ConsignDto>> resultMap = new HashMap<String, List<ConsignDto>>();
		//本次运单号情况
		groupConsignDto(entityList,resultMap);
		//解析resultMap
	    Set<Entry<String, List<ConsignDto>>> set = resultMap.entrySet();
        for (Iterator<Entry<String,  List<ConsignDto>>> it = set.iterator(); it.hasNext();) {
            Entry<String,  List<ConsignDto>> entry = (Entry<String, List<ConsignDto>>) it.next();
            List<ConsignDto> consignDtosByNo= entry.getValue();
            Integer cAmount = 0;
            //生成运单主表id
            final String consignGuid = IdentifieUtil.getGuId();
            for(ConsignDto consignDtoDetail:consignDtosByNo){
            	consignDtoDetail.setConsignId(consignGuid);
            	consignDtoDetail.setConsignFsate(ConsignFstate.UNRETURN);
            	consignDtoDetail.setCreateDate(new Date());
            	consignDtoDetail.setCreateUserId(userInfo.getUserId());
            	consignDtoDetail.setModifyUserId(userInfo.getUserId());
            	consignDtoDetail.setModifyDate(new Date());
            	consignDtoDetail.setOrgId(userInfo.getOrgId());
            	cAmount = cAmount + consignDtoDetail.getAmount();
            	consignDetails.add(consignDtoDetail);
            }
            consignDtosByNo.get(0).setCamount(cAmount);
            consigns.add(consignDtosByNo.get(0));
        }
		//分页批量插入运单主表
		LocalCollectionUtils.paginationProcess(consigns, 1000, new Processer<List<ConsignDto>>(){
			@Override
			public Object process(List<ConsignDto> list) throws Exception {
				return consignMapper.importConsigns(list);
			}
		});
		//分页批量插入运单明细表
		LocalCollectionUtils.paginationProcess(consignDetails, 1000, new Processer<List<ConsignDto>>(){
			@Override
			public Object process(List<ConsignDto> list) throws Exception {
				return consignDetailMapper.importConsignDetails(list);
			}
		});
	}
	
	//根据consignDto里的某个字段分组
	 public void groupConsignDto(List<ConsignDto> list, Map<String, List<ConsignDto>> map) {  
	        if (null == list || null == map) {  
	            return;  
	        }  	  
	        // 按运单号开始分组  
	        String key;  
	        List<ConsignDto> listTmp;  
	        for (ConsignDto val : list) {  
	            key = val.getConsignNo();  
	            listTmp = map.get(key); 
	            if (null == listTmp) {  
	                listTmp = new ArrayList<ConsignDto>();  
	                map.put(key, listTmp);  
	            }  
	            listTmp.add(val);  
	        }  
	    }

	@Override
	public List<Map<String, Object>> findConsignList(Pager<Map<String, Object>> pager) {
		// TODO Auto-generated method stub
		return consignMapper.findConsignList(pager);
	}

	@Override
	public List<Map<String, Object>> searchDispatchInfo(Pager<Map<String, Object>> pager) {
		// TODO Auto-generated method stub
		return despatchPlanMapper.searchDispatchInfo(pager);
	}

	@Override
	public void isWithCarrier(String[] consignNos) throws ValidationException {
		// TODO Auto-generated method stub
		String[] withCarrierCns = desplanConnoMapper.isWithCarrier(consignNos);
		if(withCarrierCns != null && withCarrierCns.length > 0){
			throw new ValidationException("以下运单已安排承运商带，确定要更改承运商？"+StringUtils.join(withCarrierCns, ","));
		}
	}

	@Override
	public void savePlan(DespatchPlan despatchPlan, String[] consignDetailIds, String[] consignNos) {
		// TODO Auto-generated method stub
		/**一、发运计划和关联信息**/
		//新增发运计划信息
		despatchPlan.setDespatchPlanId(IdentifieUtil.getGuId());
		this.insertInfo(despatchPlan);
		//批量添加发运计划和运单明细的关系
		consignDetailMapper.batchUpdatePlan(consignDetailIds,despatchPlan.getDespatchPlanId());
		/**二、更新本次发运计划的承运商所带运单信息**/
		//1 批量删除运单号原来关联的发运计划
		desplanConnoMapper.batchDeleteByCnos(consignNos);
		//2 批量增加运单号新的关系
		desplanConnoMapper.batchInsertByCnos(consignNos,despatchPlan.getDespatchPlanId());
		/**三、结费调整**/
//		删除本次编板的送货单明细的结费信息
		chargeInfoMapper.batchDeleteByCDetails(consignDetailIds);
//		批量增加本次运单明细结费信息(收入结费)
		chargeInfoMapper.batchProfitInsertByCDetails(consignDetailIds,despatchPlan);
//		批量增加本次运单明细结费信息(成本结费)
		chargeInfoMapper.batchCostInsertByCDetails(consignDetailIds,despatchPlan);
		/**四、更新发运计划整体信息**/
//		删除没有运单明细关联的发运计划
	}

	@Override
	public void deleteConsignDetail(String[] consignDetailIds) {
		// TODO Auto-generated method stub
//		删除运单明细
		consignDetailMapper.batchDeleteByIds(consignDetailIds);
//		删除运单明细的结费信息
		chargeInfoMapper.batchDeleteByCDetails(consignDetailIds);
//		如果运单明细关联的运单都删掉了，就删除运单
		consignMapper.clearNoUseConsign();
	}

	@Override
	public void updateConsign(List<Consign> consigns) {
		// TODO Auto-generated method stub
		consignMapper.updateConsign(consigns);
	}

	@Override
	public void insertConsignDto(ConsignDto consigndto) throws ValidationException {
		// TODO Auto-generated method stub
//		0、查询运单号是否已存在，不容许运单号重复
		Consign consign0 = new Consign();
		consign0.setConsignNo(consigndto.getConsignNo());
		List<Consign> consigns = this.searchList(consign0);
		if(consigns != null && consigns.size() > 0){
			throw new ValidationException("运单号已存在，请检查");
		}
//		1、新增运单主记录
		Consign consign = (Consign)consigndto;
		consign.setConsignId(IdentifieUtil.getGuId());
		this.insertInfo(consign);
//		2、新增运单明细记录
		ConsignDetail consignDetail = new ConsignDetail();
		consignDetail.setConsignDetailId(IdentifieUtil.getGuId());
		consignDetail.setConsignId(consign.getConsignId());
		consignDetail.setAmount(1);
		consignDetail.setCarBrand(consigndto.getCarBrand());
		consignDetail.setCarModel(consigndto.getCarModel());
		consignDetail.setChassisNo(consigndto.getChassisNo());
		consignDetail.setDespatchFstate(CustomConst.ConsignDetailFstate.UNMAKE);
		consignDetail.setModifyDate(new Date());
		consignDetail.setModifyUserId(consigndto.getModifyUserId());
		this.insertInfo(consignDetail);
	}

}
