package com.ylsh.survey.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ylsh.survey.dto.NaireAnswerDataRecord;
import com.ylsh.survey.mapper.TbBriefAnswerMapper;
import com.ylsh.survey.mapper.TbRespondentInfoMapper;
import com.ylsh.survey.pojo.TbRespondentInfo;
import com.ylsh.survey.pojo.TbRespondentInfoExample;
import com.ylsh.survey.service.ProvinceService;
import com.ylsh.survey.service.RespondentInfoService;
import com.ylsh.survey.util.TimeFormat;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;



/**
 * @description: 答题人信息管理Service
 * @author ylsh
 * @date 2018年4月24日 下午6:06:33
 */
@Service
public class RespondentInfoServiceImpl implements RespondentInfoService {

	@Autowired
	private TbRespondentInfoMapper respondentInfoMapper;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private TbBriefAnswerMapper briefAnswerMapper;


	/**
	 * @description: 根据问卷ID查询答题者的信息
	 * @author ylsh
	 * @date 2018年4月25日 下午2:37:44 
	 * @param naireId
	 * @return
	 * @see com.ylsh.survey.service.RespondentInfoService#listByNaireId(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<TbRespondentInfo> listByNaireId(Long naireId) {
		TbRespondentInfoExample example = new TbRespondentInfoExample();
		TbRespondentInfoExample.Criteria criteria = example.createCriteria();
		criteria.andNaireIdEqualTo(naireId);
		example.setOrderByClause("start_time asc");
		return respondentInfoMapper.selectByExample(example);
	}


	/**
	 * @description: 根据主键删除数据（答卷记录会被级联删除）
	 * @author ylsh
	 * @date 2018年4月25日 下午2:44:47 
	 * @param id
	 * @return
	 * @see com.ylsh.survey.service.RespondentInfoService#deleteByPrimaryKey(java.lang.Long)
	 */
	@Transactional
	@CacheEvict(value="NaireCache", key="'areaStatistic' + #naireId")
	@Override
	public int delete(Long naireId, Long respondentId) {
		// 删除简答题数据信息（若有）
		briefAnswerMapper.deleteByNaireIdAndRespondentId(naireId, respondentId);		
		// 删除答题者信息（答卷信息会被级联删除）
		return respondentInfoMapper.deleteByPrimaryKey(respondentId);
	}


	/**
	 * @description: 保存答题者信息
	 * @author ylsh
	 * @date 2018年4月25日 下午2:47:27 
	 * @param request
	 * @param ip
	 * @return
	 * @see com.ylsh.survey.service.RespondentInfoService#save(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Transactional
	@CacheEvict(value="NaireCache", key="'areaStatistic' + #naireId")
	@Override
	public long save(HttpServletRequest request, NaireAnswerDataRecord answerDataRecord) {
		TbRespondentInfo respondentInfo = new TbRespondentInfo();
		respondentInfo.setIp(answerDataRecord.getIp());
		respondentInfo.setNaireId(answerDataRecord.getNaireId());

		// 获取答题开始时间并计算答题时长
	//	Date startTime = (Date) request.getSession().getAttribute("startTime");
		Date startTime = answerDataRecord.getStartTime();
		if (startTime == null) {
			throw new RuntimeException("刷新重试!");
		}
		respondentInfo.setStartTime(startTime);
		respondentInfo.setEndTime(new Date());
		String duration = TimeFormat.formatDuration(respondentInfo.getStartTime(), respondentInfo.getEndTime());
		respondentInfo.setDuration(duration);

		// 设置答题人来源信息
		int provinceId = provinceService.getByCode(answerDataRecord.getCityCode()).getId();
		respondentInfo.setProvinceId(provinceId);

		// 分析并记录答题者浏览器及系统信息
		String ua = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(ua); 
		Browser browser = userAgent.getBrowser();  
		OperatingSystem os = userAgent.getOperatingSystem();
		respondentInfo.setSystem(os.getName());
		respondentInfo.setBrowser(browser.getName() + browser.getVersion(ua).getVersion());
		respondentInfoMapper.insert(respondentInfo);
		return respondentInfo.getId();
	}


	/**
	 * @description: 查询答题者详细信息
	 * @author ylsh
	 * @date 2018年4月25日 下午4:34:48 
	 * @param id
	 * @return
	 * @see com.ylsh.survey.service.RespondentInfoService#get(java.lang.Long)
	 */
	@Transactional(readOnly=true)
	@Override
	public TbRespondentInfo get(Long id) {
		return respondentInfoMapper.getDetailAndAddress(id);
	}


	/**
	 * @description: 删除问卷对应的全部答题者信息
	 * @author ylsh
	 * @date 2018年4月26日 下午4:33:10 
	 * @param naireId
	 * @see com.ylsh.survey.service.RespondentInfoService#deleteByNaireId(long)
	 */
	@Transactional
	@CacheEvict(value="NaireCache", key="'areaStatistic' + #naireId")
	@Override
	public void deleteByNaireId(long naireId) {
		TbRespondentInfoExample example = new TbRespondentInfoExample();
		TbRespondentInfoExample.Criteria criteria = example.createCriteria();
		criteria.andNaireIdEqualTo(naireId);
		respondentInfoMapper.deleteByExample(example);
	}



}
