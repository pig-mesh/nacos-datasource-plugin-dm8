package com.pig4cloud.plugin.impl.dm;

import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoGrayMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.pig4cloud.plugin.constants.DataSourceConstant;

import java.util.Collections;

public class ConfigInfoGrayMapperByDm extends DmAbstractMapper implements ConfigInfoGrayMapper {

	@Override
	public MapperResult findAllConfigInfoGrayForDumpAllFetchRows(MapperContext context) {
		String sql = " SELECT id,data_id,group_id,tenant_id,gray_name,gray_rule,app_name,content,md5,gmt_modified  FROM  config_info_gray  ORDER BY id LIMIT "
				+ context.getStartRow() + "," + context.getPageSize();
		return new MapperResult(sql, Collections.emptyList());
	}

	@Override
	public String getDataSource() {
		return DataSourceConstant.DM;
	}

}
