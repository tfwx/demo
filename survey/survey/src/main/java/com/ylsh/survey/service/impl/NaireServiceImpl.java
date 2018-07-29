package com.ylsh.survey.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylsh.survey.mapper.TbNaireMapper;
import com.ylsh.survey.mapper.TbOptionMapper;
import com.ylsh.survey.mapper.TbQuestionMapper;
import com.ylsh.survey.pojo.TbNaire;
import com.ylsh.survey.pojo.TbNaireExample;
import com.ylsh.survey.pojo.TbOption;
import com.ylsh.survey.pojo.TbQuestion;
import com.ylsh.survey.service.NaireService;
import com.ylsh.survey.util.DownloadUtil;
import com.ylsh.survey.util.POIUtil;

/**
 * @description:问卷管理Service
 * @author ylsh
 * @date 2017年11月30日 下午4:46:21
 */
@Service
public class NaireServiceImpl implements NaireService {

	@Autowired
	private TbNaireMapper naireMapper;

	@Autowired
	private TbQuestionMapper questionMapper;

	@Autowired
	private TbOptionMapper optionMapper;


	@Value("${DEFAULT_PAGE_SIZE}")
	private String DEFAULT_PAGE_SIZE;



	/**
	 * @description:问卷分页查询
	 * @author ylsh
	 * @date 2017年11月30日 下午4:46:49
	 * @param queryParameter
	 * @return
	 * @see com.ylsh.survey.service.NaireService#list(java.util.Map)
	 */
	@Transactional(readOnly = true)
	@Override
	public PageInfo<TbNaire> list(Map<String, Object> queryParameter) {
		// 获取分页参数
		Object pageNum = queryParameter.get("pageNum");
		Object pageSize = queryParameter.get("pageSize");
		// 分页参数为空时，设置默认分页参数
		if (pageNum == null || pageSize == null) {
			pageNum = 1;
			pageSize = DEFAULT_PAGE_SIZE;
			queryParameter.put("pageNum", pageNum);
			queryParameter.put("pageSize", DEFAULT_PAGE_SIZE);
		}		
		// 设置分页信息
		PageHelper.startPage(Integer.parseInt(pageNum.toString()), Integer.parseInt(pageSize.toString()));
		// 执行查询
		List<TbNaire> naireList = naireMapper.list(queryParameter);
		// 取查询结果
		PageInfo<TbNaire> pageInfo = new PageInfo<TbNaire>(naireList);
		// 返回数据
		return pageInfo;
	}


	/**
	 * @description:新增问卷
	 * @author ylsh
	 * @date 2017年11月30日 下午4:47:52
	 * @param naire
	 * @return
	 * @see com.ylsh.survey.service.NaireService#save(com.ylsh.survey.pojo.TbNaire)
	 */
	@Transactional
	@Override
	public void save(TbNaire naire) {
		if (naire.getQuestionList().isEmpty()) {
			throw new RuntimeException("问卷数据异常");
		}

		// 补全属性
		naire.setCreateTime(new Date());
		naire.setEndTime(naire.getCreateTime());
		naire.setCitationsCount(0L);
		naire.setStatus((byte) 1);	// 默认收集中	状态：1(未发布) 2(收集中) 3(已结束)
		naire.setDelFlag((byte) 0); // 删除标记(默认为0)
		naire.setShare(true);		// 默认共享
		naire.setSubject((byte) 0);	// 默认白色主题

		// 插入记录

		// 1. 插入问卷记录
		naireMapper.insert(naire);
		// 2. 插入问题记录
		// 补全父ID
		for (TbQuestion question : naire.getQuestionList()) {
			question.setNaireId(naire.getId());
		}
		// 批量插入记录
		questionMapper.batchInsert(naire.getQuestionList());

		// 3. 插入选项记录
		List<TbOption> optionList = new ArrayList<TbOption>();
		for (TbQuestion question : naire.getQuestionList()) {
			for (TbOption option : question.getOptionList()) {
				// 补全父ID
				option.setQuestionId(question.getId());
				option.setSelectCount(0L);
			}
			optionList.addAll(question.getOptionList());
		}
		// 批量插入记录
		if (!optionList.isEmpty()) {
			optionMapper.batchInsert(optionList);
		}
	}

	/**
	 * @description:删除问卷(彻底删除)
	 * @author ylsh
	 * @date 2017年11月30日 下午4:48:51
	 * @param naireIds
	 * @see com.ylsh.survey.service.NaireService#delete(java.lang.Long[])
	 */
	@Transactional
	@Override
	@Caching( evict = {
		@CacheEvict(value="NaireCache", key="'naire' + #naireIds[0]"),
		@CacheEvict(value="NaireCache", key="'areaStatistic' + #naireIds[0]")
	})
	public void delete(Long[] naireIds) {
		// 级联删除，也会删除与该问卷相关的数据（问题，选项，答案，分条数据，来源数据等）
		TbNaireExample example = new TbNaireExample();
		TbNaireExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(Arrays.asList(naireIds));
		naireMapper.deleteByExample(example);
	}

	/**
	 * @description: 删除问卷(移至回收站)
	 * @author ylsh
	 * @date 2018年2月27日 下午12:05:51 
	 * @param naireId
	 * @see com.ylsh.survey.service.NaireService#recycle(java.lang.Long)
	 */
	@Transactional
	@Override
	@Caching( evict = {
		@CacheEvict(value="NaireCache", key="'naire' + #naireId"),
		@CacheEvict(value="NaireCache", key="'areaStatistic' + #naireId")
	})
	public void recycle(Long naireId) {
		TbNaire naire = new TbNaire();
		naire.setId(naireId);
		naire.setDelFlag((byte) 1); 
		naire.setEndTime(new Date());
		naireMapper.updateByPrimaryKeySelective(naire);
	}

	/**
	 * @description: 获取回收站数据
	 * @author ylsh
	 * @date 2018年2月27日 下午2:43:23 
	 * @param userId
	 * @return
	 * @see com.ylsh.survey.service.NaireService#getRecycledData(java.lang.Long)
	 */
	@Override
	public List<TbNaire> getRecycledData(Long userId) {
		return naireMapper.getRecycledData(userId);
	}


	/**
	 * @description: 恢复问卷(从回收站恢复)
	 * @author ylsh
	 * @date 2018年2月27日 下午1:26:09 
	 * @param naireIds
	 * @see com.ylsh.survey.service.NaireService#restore(java.lang.Long)
	 */
	@Transactional
	@Override
	public void restore(Long[] naireIds) {
		TbNaire naire = new TbNaire();
		naire.setDelFlag((byte) 0);
		TbNaireExample example = new TbNaireExample();
		TbNaireExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(Arrays.asList(naireIds));
		naireMapper.updateByExampleSelective(naire, example);
	}

	/**
	 * @description:更新问卷
	 * @author ylsh
	 * @date 2017年11月30日 下午4:49:03
	 * @param naire
	 * @see com.ylsh.survey.service.NaireService#update(com.ylsh.survey.pojo.TbNaire)
	 */
	@Transactional
	@Override
	@CacheEvict(value="NaireCache", key="'naire' + #naire.id")
	public void update(TbNaire naire) {
		naire.setEndTime(new Date());
		naireMapper.updateByPrimaryKeySelective(naire);
	}

	/**
	 * @description:根据问卷ID获取问卷
	 * @author ylsh
	 * @date 2017年11月30日 下午4:50:45
	 * @param naireId
	 * @return
	 * @see com.ylsh.survey.service.NaireService#get(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Cacheable(value="NaireCache", key="'naire' + #naireId")
	@Override
	public TbNaire get(Long naireId) {
		return naireMapper.selectByPrimaryKey(naireId);
	}



	/**
	 * @description:下载问卷(Word)
	 * @author ylsh
	 * @date 2017年11月30日 下午4:52:07
	 * @param naireId
	 * @param response
	 * @throws IOException 
	 * @see com.ylsh.survey.service.NaireService#downloadNaire(java.lang.Long, javax.servlet.http.HttpServletResponse)
	 */
	@Transactional(readOnly = true)
	@Override
	public void downloadNaire(HttpServletResponse response, Long naireId) {
		// 用户可下载：未删除、其它用户已共享、已发布的问卷
		TbNaireExample example = new TbNaireExample();
		TbNaireExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(naireId).andDelFlagEqualTo((byte) 0).andShareEqualTo(true).andStatusNotEqualTo((byte) 1);
		List<TbNaire> naireList = naireMapper.selectByExample(example);
		if (naireList == null || naireList.isEmpty()) {
			// 该问卷不存在
			response.setCharacterEncoding("utf-8");
			try {
				response.getWriter().println("该问卷不存在！");
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			TbNaire naire = naireList.get(0);
			try {
				// 生成问卷
				File naireDocFile = POIUtil.createNaireDocument(naire);
				// 返回给客户端
				DownloadUtil.download(naireDocFile, naire.getTitle() + ".docx", response);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * @description: 查询问卷的基本统计数据
	 * @author ylsh
	 * @date 2017年12月6日 上午9:25:43
	 * @param naireId
	 * @return
	 * @see com.ylsh.survey.service.NaireService#dataStatistics(java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public TbNaire getStatisticsData(Long naireId) {
		return naireMapper.getStatisticsData(naireId);
	}



	/**
	 * @description:复制其他用户的问卷
	 * @author ylsh
	 * @date 2017年12月28日 上午10:01:10
	 * @param naireId
	 * @return
	 * @see com.ylsh.survey.service.NaireService#copyNaire(java.lang.Long)
	 */
	@Transactional
	@Override
	public void copyNaire(Long userId, Long naireId) {
		/*// 先判断当前用户是否已经引用过该问卷，避免重复引用
		TbNaireExample example = new TbNaireExample();
		TbNaireExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andIdEqualTo(naireId);
		int count = naireMapper.countByExample(example);
		if (count > 0) {
			return ResponseResult.error("您已经引用过改模板");
		}*/

		// 获取问卷模板
		TbNaire naire = naireMapper.selectByPrimaryKey(naireId);

		// 重设问卷属性
		naire.setId(null);
		naire.setUserId(userId);
		naire.setPassword(null);
		for (TbQuestion question : naire.getQuestionList()) {
			question.setId(null);
			question.setNaireId(null);
			for (TbOption option : question.getOptionList()) {
				option.setId(null);
				option.setQuestionId(null);
			}
		}

		// 开始复制模板
		save(naire);
	}



	/**
	 * @description: 判断问卷是否属于用户
	 * @author ylsh
	 * @date 2018年4月25日 下午4:45:23 
	 * @param userId
	 * @param naireId
	 * @return
	 * @see com.ylsh.survey.service.NaireService#isNaireBelongToUser(java.lang.Long, java.lang.Long)
	 */
	@Transactional(readOnly = true)
	@Override
	public boolean isNaireBelongToUser(Long userId, Long naireId) {
		TbNaireExample example = new TbNaireExample();
		TbNaireExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId).andIdEqualTo(naireId);		
		return naireMapper.countByExample(example) == 1;
	}

}