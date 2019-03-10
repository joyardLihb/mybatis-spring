package tk.mybatis.web.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.istack.internal.NotNull;

import tk.mybatis.web.mapper.SysDictMapper;
import tk.mybatis.web.model.SysDict;
import tk.mybatis.web.service.DictService;
@Service
@Transactional
public class DictServiceImpl implements DictService {
	@Autowired
	private SysDictMapper sysDictMapper;
	
	@Override
	public SysDict findById(@NotNull Long id) {
		return sysDictMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SysDict> findBySysDict(SysDict sysDict, Integer offset,
			Integer limit) {
		RowBounds rowBounds = RowBounds.DEFAULT;
		if(offset != null && limit != null){
			rowBounds = new RowBounds(offset,limit);
		}
		return sysDictMapper.selectBySysDict(sysDict, rowBounds);
	}

	@Override
	public boolean saveOrUpdate(SysDict sysDict) {
		if(sysDict.getId() == null){
			return sysDictMapper.insert(sysDict) == 1;
		}
		else{
			return sysDictMapper.updateByPrimaryKey(sysDict) == 1;
		}
	}

	@Override
	public boolean deleteById(@NotNull Long id) {
		return sysDictMapper.deleteByPrimaryKey(id) == 1;
	}

}
