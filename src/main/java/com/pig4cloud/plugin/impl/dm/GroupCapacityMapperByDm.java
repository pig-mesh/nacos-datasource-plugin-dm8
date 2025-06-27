package com.pig4cloud.plugin.impl.dm;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.NamespaceUtil;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.mapper.GroupCapacityMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.pig4cloud.plugin.constants.DataSourceConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupCapacityMapperByDm extends DmAbstractMapper implements GroupCapacityMapper {

	@Override
	public MapperResult selectGroupInfoBySize(MapperContext context) {
		String sql = "SELECT id, group_id FROM group_capacity WHERE id > ? LIMIT ?";
		return new MapperResult(sql,
				CollectionUtils.list(context.getWhereParameter(FieldConstant.ID), context.getPageSize()));
	}

	@Override
	public MapperResult select(MapperContext context) {
		String sql = "SELECT id, quota, \"usage\", max_size, max_aggr_count, max_aggr_size, group_id FROM group_capacity "
				+ "WHERE group_id = ?";
		return new MapperResult(sql, Collections.singletonList(context.getWhereParameter(FieldConstant.GROUP_ID)));
	}

	@Override
	public MapperResult insertIntoSelect(MapperContext context) {
		List<Object> paramList = new ArrayList<>();
		paramList.add(context.getUpdateParameter(FieldConstant.GROUP_ID));
		paramList.add(context.getUpdateParameter(FieldConstant.QUOTA));
		paramList.add(context.getUpdateParameter(FieldConstant.MAX_SIZE));
		paramList.add(context.getUpdateParameter(FieldConstant.MAX_AGGR_COUNT));
		paramList.add(context.getUpdateParameter(FieldConstant.MAX_AGGR_SIZE));
		paramList.add(context.getUpdateParameter(FieldConstant.GMT_CREATE));
		paramList.add(context.getUpdateParameter(FieldConstant.GMT_MODIFIED));

		String sql = "INSERT INTO group_capacity (group_id, quota, \"usage\", max_size, max_aggr_count, max_aggr_size,gmt_create,"
				+ " gmt_modified) SELECT ?, ?, count(*), ?, ?, ?, ?, ? FROM config_info";
		return new MapperResult(sql, paramList);
	}

	@Override
	public MapperResult insertIntoSelectByWhere(MapperContext context) {
		final String sql = "INSERT INTO group_capacity (group_id, quota, \"usage\", max_size, max_aggr_count, max_aggr_size, gmt_create,"
				+ " gmt_modified) SELECT ?, ?, count(*), ?, ?, ?, ?, ? FROM config_info WHERE group_id=? AND tenant_id = '"
				+ NamespaceUtil.getNamespaceDefaultId() + "'";
		List<Object> paramList = new ArrayList<>();
		paramList.add(context.getUpdateParameter(FieldConstant.GROUP_ID));
		paramList.add(context.getUpdateParameter(FieldConstant.QUOTA));
		paramList.add(context.getUpdateParameter(FieldConstant.MAX_SIZE));
		paramList.add(context.getUpdateParameter(FieldConstant.MAX_AGGR_COUNT));
		paramList.add(context.getUpdateParameter(FieldConstant.MAX_AGGR_SIZE));
		paramList.add(context.getUpdateParameter(FieldConstant.GMT_CREATE));
		paramList.add(context.getUpdateParameter(FieldConstant.GMT_MODIFIED));

		paramList.add(context.getWhereParameter(FieldConstant.GROUP_ID));

		return new MapperResult(sql, paramList);
	}

	@Override
	public MapperResult incrementUsageByWhereQuotaEqualZero(MapperContext context) {
		return new MapperResult(
				"UPDATE group_capacity SET \"usage\" = \"usage\" + 1, gmt_modified = ? WHERE group_id = ? AND \"usage\" < ? AND quota = 0",
				CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.GROUP_ID),
						context.getWhereParameter(FieldConstant.USAGE)));
	}

	@Override
	public MapperResult incrementUsageByWhereQuotaNotEqualZero(MapperContext context) {
		return new MapperResult(
				"UPDATE group_capacity SET \"usage\" = \"usage\" + 1, gmt_modified = ? WHERE group_id = ? AND \"usage\" < quota AND quota != 0",
				CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.GROUP_ID)));
	}

	@Override
	public MapperResult incrementUsageByWhere(MapperContext context) {
		return new MapperResult(
				"UPDATE group_capacity SET \"usage\" = \"usage\" + 1, gmt_modified = ? WHERE group_id = ?",
				CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.GROUP_ID)));
	}

	@Override
	public MapperResult decrementUsageByWhere(MapperContext context) {
		return new MapperResult(
				"UPDATE group_capacity SET \"usage\" = \"usage\" - 1, gmt_modified = ? WHERE group_id = ? AND \"usage\" > 0",
				CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.GROUP_ID)));
	}

	@Override
	public MapperResult updateUsage(MapperContext context) {
		return new MapperResult(
				"UPDATE group_capacity SET \"usage\" = (SELECT count(*) FROM config_info), gmt_modified = ? WHERE group_id = ?",
				CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.GROUP_ID)));
	}

	@Override
	public MapperResult updateUsageByWhere(MapperContext context) {
		return new MapperResult(
				"UPDATE group_capacity SET \"usage\" = (SELECT count(*) FROM config_info WHERE group_id=? AND tenant_id = '"
						+ NamespaceUtil.getNamespaceDefaultId() + "')," + " gmt_modified = ? WHERE group_id= ?",
				CollectionUtils.list(context.getWhereParameter(FieldConstant.GROUP_ID),
						context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.GROUP_ID)));
	}

	@Override
	public String getDataSource() {
		return DataSourceConstant.DM;
	}

}
