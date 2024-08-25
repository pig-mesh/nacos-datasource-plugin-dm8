package com.pig4cloud.plugin.impl.dm;

import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.pig4cloud.plugin.constants.DataSourceConstant;

/**
 * DM 抽象映射器
 *
 * @author lengleng
 * @date 2024/08/25
 */
public abstract class DmAbstractMapper extends AbstractMapper {

	/**
	 * 获取数据源
	 * @return {@link String }
	 */
	@Override
	public String getDataSource() {
		return DataSourceConstant.DM;
	}

	/**
	 * get 函数
	 * @param functionName 函数名称
	 * @return {@link String }
	 */
	@Override
	public String getFunction(String functionName) {
		return "NOW()";
	}

}
