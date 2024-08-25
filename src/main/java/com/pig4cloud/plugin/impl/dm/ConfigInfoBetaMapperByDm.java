package com.pig4cloud.plugin.impl.dm;

import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoBetaMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.pig4cloud.plugin.constants.DataSourceConstant;

import java.util.ArrayList;
import java.util.List;

public class ConfigInfoBetaMapperByDm extends DmAbstractMapper implements ConfigInfoBetaMapper {

	@Override
	public MapperResult findAllConfigInfoBetaForDumpAllFetchRows(MapperContext context) {
		int startRow = context.getStartRow();
		int pageSize = context.getPageSize();
		String sql = " SELECT t.id,data_id,group_id,tenant_id,app_name,content,md5,gmt_modified,beta_ips,encrypted_data_key "
				+ " FROM ( SELECT id FROM config_info_beta  ORDER BY id LIMIT " + startRow + "," + pageSize + " )"
				+ "  g, config_info_beta t WHERE g.id = t.id ";
		List<Object> paramList = new ArrayList<>();
		paramList.add(startRow);
		paramList.add(pageSize);

		return new MapperResult(sql, paramList);
	}

	@Override
	public String getDataSource() {
		return DataSourceConstant.DM;
	}

}
