package com.pig4cloud.plugin.impl.dm;

import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.mapper.AbstractMapper;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigTagsRelationMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.pig4cloud.plugin.constants.DataSourceConstant;

import java.util.ArrayList;
import java.util.List;

public class ConfigTagsRelationMapperByDm extends AbstractMapper implements ConfigTagsRelationMapper {

	@Override
	public MapperResult findConfigInfo4PageFetchRows(MapperContext context) {
		final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
		final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
		final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
		final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
		final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
		final String[] tagArr = (String[]) context.getWhereParameter(FieldConstant.TAG_ARR);

		List<Object> paramList = new ArrayList<>();
		StringBuilder where = new StringBuilder(" WHERE ");
		final String sql = "SELECT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content FROM config_info  a LEFT JOIN "
				+ "config_tags_relation b ON a.id=b.id";

		where.append(" a.tenant_id=? ");
		paramList.add(tenant);

		if (StringUtils.isNotBlank(dataId)) {
			where.append(" AND a.data_id=? ");
			paramList.add(dataId);
		}
		if (StringUtils.isNotBlank(group)) {
			where.append(" AND a.group_id=? ");
			paramList.add(group);
		}
		if (StringUtils.isNotBlank(appName)) {
			where.append(" AND a.app_name=? ");
			paramList.add(appName);
		}
		if (!StringUtils.isBlank(content)) {
			where.append(" AND a.content LIKE ? ");
			paramList.add(content);
		}
		where.append(" AND b.tag_name IN (");
		for (int i = 0; i < tagArr.length; i++) {
			if (i != 0) {
				where.append(", ");
			}
			where.append('?');
			paramList.add(tagArr[i]);
		}
		where.append(") ");
		return new MapperResult(sql + where + " LIMIT " + context.getStartRow() + "," + context.getPageSize(),
				paramList);
	}

	@Override
	public MapperResult findConfigInfoLike4PageFetchRows(MapperContext context) {
		final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
		final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
		final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
		final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
		final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
		final String[] tagArr = (String[]) context.getWhereParameter(FieldConstant.TAG_ARR);

		List<Object> paramList = new ArrayList<>();

		StringBuilder where = new StringBuilder(" WHERE ");
		final String sqlFetchRows = "SELECT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content "
				+ "FROM config_info a LEFT JOIN config_tags_relation b ON a.id=b.id ";

		where.append(" a.tenant_id LIKE ? ");
		paramList.add(tenant);
		if (!StringUtils.isBlank(dataId)) {
			where.append(" AND a.data_id LIKE ? ");
			paramList.add(dataId);
		}
		if (!StringUtils.isBlank(group)) {
			where.append(" AND a.group_id LIKE ? ");
			paramList.add(group);
		}
		if (!StringUtils.isBlank(appName)) {
			where.append(" AND a.app_name = ? ");
			paramList.add(appName);
		}
		if (!StringUtils.isBlank(content)) {
			where.append(" AND a.content LIKE ? ");
			paramList.add(content);
		}

		where.append(" AND b.tag_name IN (");
		for (int i = 0; i < tagArr.length; i++) {
			if (i != 0) {
				where.append(", ");
			}
			where.append('?');
			paramList.add(tagArr[i]);
		}
		where.append(") ");
		return new MapperResult(sqlFetchRows + where + " LIMIT " + context.getStartRow() + "," + context.getPageSize(),
				paramList);
	}

	@Override
	public String getDataSource() {
		return DataSourceConstant.DM;
	}

}
