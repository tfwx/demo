package com.ylsh.survey.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ylsh.survey.dto.NaireAnswerDataRecord;
import com.ylsh.survey.mapper.TbAnswerRecordMapper;
import com.ylsh.survey.mapper.TbBriefAnswerMapper;
import com.ylsh.survey.mapper.TbNaireMapper;
import com.ylsh.survey.pojo.TbBriefAnswer;
import com.ylsh.survey.pojo.TbNaire;
import com.ylsh.survey.pojo.TbOption;
import com.ylsh.survey.pojo.TbQuestion;
import com.ylsh.survey.pojo.TbRespondentInfo;
import com.ylsh.survey.service.AnswerRecordService;
import com.ylsh.survey.service.RespondentInfoService;
import com.ylsh.survey.util.DownloadUtil;
import com.ylsh.survey.util.NumberFormatUtil;
import com.ylsh.survey.util.POIUtil;
import com.ylsh.survey.util.ZipUtils;


/**
 * @description: 问卷答案管理Service
 * @author ylsh
 * @date 2018年4月25日 下午2:50:22
 */
@Service
public class AnswerRecordServiceImpl implements AnswerRecordService {
	
	@Autowired
	private TbAnswerRecordMapper answerRecordMapper;
	
	@Autowired
	private TbBriefAnswerMapper briefAnswerMapper;
	
	@Autowired
	private RespondentInfoService respondentInfoService;
	
	@Autowired
	private TbNaireMapper naireMapper;
	

	/**
	 * @description: 获取答题者问卷填写的详细信息
	 * @author ylsh
	 * @date 2018年4月25日 下午4:24:13 
	 * @param userId
	 * @param naireId
	 * @param respondentId
	 * @return
	 * @see com.ylsh.survey.service.AnswerRecordService#getDetailInfo(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<TbQuestion> getDetailInfo(Long naireId, Long respondentId) {
		return answerRecordMapper.getDetailInfoByRespondent(naireId, respondentId);
	}


	/**
	 * @description: 保存答卷信息
	 * @author ylsh
	 * @date 2018年4月25日 下午7:46:52 
	 * @param answerDataRecord
	 * @return
	 * @see com.ylsh.survey.service.AnswerRecordService#save(com.ylsh.survey.dto.NaireAnswerDataRecord)
	 */
	@Transactional
	@Override
	@CacheEvict(value="NaireCache", key="'areaStatistic' + #answerDataRecord.naireId")
	public void record(HttpServletRequest resuest, NaireAnswerDataRecord answerDataRecord) {
		// 保存答题者信息
		long respondentInfoId = respondentInfoService.save(resuest, answerDataRecord);
		answerDataRecord.setRespondentInfoId(respondentInfoId);
		
		// 保存简答题信息
		List<TbBriefAnswer> briefAnswerList = answerDataRecord.getBriefAnswerList();
		if (briefAnswerList != null && !briefAnswerList.isEmpty()) {
			briefAnswerMapper.batchInsert(briefAnswerList);
			// 记录简答题ID
			List<TbOption> oprionAnswerList = answerDataRecord.getOptionAnswerList();
			for (TbBriefAnswer briefAnswer : briefAnswerList) {
				TbOption optionAnswer = new TbOption();
				optionAnswer.setQuestionId(briefAnswer.getQuestionId());
				optionAnswer.setId(briefAnswer.getId());
				oprionAnswerList.add(optionAnswer);
			}
		}
		// 存储答卷
		answerRecordMapper.insertRecord(answerDataRecord);
	}


	/**
	 * @description: 统计总数据收集情况（显示于主页）
	 * @author ylsh
	 * @date 2018年4月26日 上午9:04:13 
	 * @return
	 * @see com.ylsh.survey.service.AnswerRecordService#getIndexStatistics()
	 */
	@Transactional(readOnly=true)
	@Override
	public Map<String, Long> getIndexStatistics() {
		return answerRecordMapper.getIndexStatistics();
	}


	/**
	 * @description: 获取问卷答案统计图表数据（只包含选择题）
	 * @author ylsh
	 * @date 2018年4月26日 下午1:04:14 
	 * @param naireId
	 * @return
	 * @see com.ylsh.survey.service.AnswerRecordService#chart(java.lang.Long)
	 */
	@Transactional(readOnly=true)
	@Override
	public List<TbQuestion> chart(Long naireId) {
		return answerRecordMapper.chart(naireId);
	}


	/**
	 * @description: 重置问卷已收集的数据（包括：答题人信息，答题数据信息：选择和简答数据）
	 * @author ylsh
	 * @date 2018年4月26日 下午4:20:36 
	 * @param naireId
	 * @see com.ylsh.survey.service.AnswerRecordService#reset(java.lang.Long)
	 */
	@Transactional
	@Override
	@Caching( evict = {
		@CacheEvict(value="NaireCache", key="'naire' + #naireId"),
		@CacheEvict(value="NaireCache", key="'areaStatistic' + #naireId")
	})
	public void reset(Long naireId) {
		// 先删除简答题的答案数据
		briefAnswerMapper.deleteByNaireIdAndRespondentId(naireId, null);
		// 再删除答案记录表中的记录（答卷数据会被级联闪出）
		respondentInfoService.deleteByNaireId(naireId);
		// 更新问卷最后一次的操作时间
		TbNaire naire = new TbNaire();
		naire.setId(naireId);
		naire.setEndTime(new Date());
		naireMapper.updateByPrimaryKeySelective(naire);
	}


	/**
	 * @description: 生成问卷答案统计数据Excel(并写入输出流)
	 * @author ylsh
	 * @date 2018年4月26日 下午7:35:57 
	 * @param response
	 * @param naireId
	 * @see com.ylsh.survey.service.AnswerRecordService#export(javax.servlet.http.HttpServletResponse, java.lang.Long)
	 */
	@Transactional(readOnly=true)
	@Override
	public void export(HttpServletResponse response, Long naireId) {
		// 获取问卷基本信息
		TbNaire nairePercentData = naireMapper.getStatisticsData(naireId);
		// 获取答卷总的数据统计情况：各选项所占百分比(该数据列表内排除简答题数据)（统计显示）
		List<TbQuestion> answerPercentList = answerRecordMapper.getNaireAnswerPercent(naireId);
		// 计算各个选项所占用百分比情况
		for (TbQuestion question :  answerPercentList) {
			double SumSelectCount = 0;
			for (TbOption option : question.getOptionList()) {
				SumSelectCount += option.getSelectCount();
			}
			if (SumSelectCount == 0) {
				SumSelectCount = 1;
			}
			for (TbOption option : question.getOptionList()) {
				String percent = NumberFormatUtil.toPercent(option.getSelectCount()/SumSelectCount);
				option.setSelectPercent(percent);
			}
		}
		nairePercentData.setQuestionList(answerPercentList);
		
		// 获取答题者信息和答卷信息（分条显示）
		List<TbRespondentInfo> respondentInfos = answerRecordMapper.getRespondentNaireData(naireId);
		
		try {
			// 生成问卷
			File dataXlsFile = POIUtil.createNaireDataExcel1(nairePercentData);
			File sDataXlsFile = POIUtil.createNaireDataExcel2(respondentInfos);
			// 压缩文件
			File zipFile = ZipUtils.toZip(true, dataXlsFile, sDataXlsFile);
			// 返回给客户端
			String fileName = respondentInfos.get(0).getNaire().getTitle() + "_All_Data_Readable.zip";
			DownloadUtil.download(zipFile, fileName, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	
}
