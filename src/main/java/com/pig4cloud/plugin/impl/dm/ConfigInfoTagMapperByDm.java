package com.pig4cloud.plugin.impl.dm;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoTagMapper;
import com.pig4cloud.plugin.constants.DataSourceConstant;

public class ConfigInfoTagMapperByDm extends DmAbstractMapper implements ConfigInfoTagMapper {

	@Override
	public String updateConfigInfo4TagCas() {
		return "UPDATE config_info_tag SET content = ?, md5 = ?, src_ip = ?,src_user = ?,gmt_modified = ?,app_name = ? "
				+ "WHERE data_id = ? AND group_id = ? AND (tenant_id = ? OR tenant_id IS NULL) AND tag_id = ? AND (md5 = ? OR md5 IS NULL OR md5 = '')";
	}

	@Override
	public String findAllConfigInfoTagForDumpAllFetchRows(int startRow, int pageSize) {
		return " SELECT t.id,data_id,group_id,tenant_id,tag_id,app_name,content,md5,gmt_modified "
				+ " FROM (  SELECT id FROM config_info_tag WHERE  ROWNUM > " + startRow + " AND ROWNUM <="
				+ (startRow + pageSize) + "ORDER BY id   " + " ) " + "g, config_info_tag t  WHERE g.id = t.id  ";
	}

	@Override
	public String getTableName() {
		return TableConstant.CONFIG_INFO_TAG;
	}

	@Override
	public String getDataSource() {
		return DataSourceConstant.DM;
	}

}
