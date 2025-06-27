package com.pig4cloud.plugin.impl.dm;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.mapper.TenantCapacityMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;
import com.pig4cloud.plugin.constants.DataSourceConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TenantCapacityMapperByDm extends DmAbstractMapper implements TenantCapacityMapper {

	@Override
	public MapperResult getCapacityList4CorrectUsage(MapperContext context) {
		String sql = "SELECT id, tenant_id FROM tenant_capacity WHERE id>? LIMIT ?";
		return new MapperResult(sql, CollectionUtils.list(context.getWhereParameter(FieldConstant.ID),
				context.getWhereParameter(FieldConstant.LIMIT_SIZE)));
	}

	@Override
	public MapperResult select(MapperContext context) {
		String sql = "SELECT id, quota, \"usage\", max_size, max_aggr_count, max_aggr_size, tenant_id FROM tenant_capacity "
				+ "WHERE tenant_id = ?";
		return new MapperResult(sql, Collections.singletonList(context.getWhereParameter(FieldConstant.TENANT_ID)));
	}

	@Override
	public MapperResult incrementUsageWithDefaultQuotaLimit(MapperContext context) {
		return new MapperResult(
				"UPDATE tenant_capacity SET \"usage\" = \"usage\" + 1, gmt_modified = ? WHERE tenant_id = ? AND \"usage\" <"
						+ " ? AND quota = 0",
				CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.TENANT_ID),
						context.getWhereParameter(FieldConstant.USAGE)));
	}

	@Override
	public MapperResult incrementUsageWithQuotaLimit(MapperContext context) {
		return new MapperResult(
				"UPDATE tenant_capacity SET \"usage\" = \"usage\" + 1, gmt_modified = ? WHERE tenant_id = ? AND \"usage\" < "
						+ "quota AND quota != 0",
				CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.TENANT_ID)));
	}

	@Override
	public MapperResult incrementUsage(MapperContext context) {
		return new MapperResult(
				"UPDATE tenant_capacity SET \"usage\" = \"usage\" + 1, gmt_modified = ? WHERE tenant_id = ?",
				CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.TENANT_ID)));
	}

	@Override
	public MapperResult decrementUsage(MapperContext context) {
		return new MapperResult(
				"UPDATE tenant_capacity SET \"usage\" = \"usage\" - 1, gmt_modified = ? WHERE tenant_id = ? AND \"usage\" > 0",
				CollectionUtils.list(context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.TENANT_ID)));
	}

	@Override
	public MapperResult correctUsage(MapperContext context) {
		return new MapperResult(
				"UPDATE tenant_capacity SET \"usage\" = (SELECT count(*) FROM config_info WHERE tenant_id = ?), "
						+ "gmt_modified = ? WHERE tenant_id = ?",
				CollectionUtils.list(context.getWhereParameter(FieldConstant.TENANT_ID),
						context.getUpdateParameter(FieldConstant.GMT_MODIFIED),
						context.getWhereParameter(FieldConstant.TENANT_ID)));
	}

	@Override
	public MapperResult insertTenantCapacity(MapperContext context) {
		List<Object> paramList = new ArrayList<>();
		paramList.add(context.getUpdateParameter(FieldConstant.TENANT_ID));
		paramList.add(context.getUpdateParameter(FieldConstant.QUOTA));
		paramList.add(context.getUpdateParameter(FieldConstant.MAX_SIZE));
		paramList.add(context.getUpdateParameter(FieldConstant.MAX_AGGR_COUNT));
		paramList.add(context.getUpdateParameter(FieldConstant.MAX_AGGR_SIZE));
		paramList.add(context.getUpdateParameter(FieldConstant.GMT_CREATE));
		paramList.add(context.getUpdateParameter(FieldConstant.GMT_MODIFIED));
		paramList.add(context.getWhereParameter(FieldConstant.TENANT_ID));

		return new MapperResult(
				"INSERT INTO tenant_capacity (tenant_id, quota, \"usage\", max_size, max_aggr_count, max_aggr_size, "
						+ "gmt_create, gmt_modified) SELECT ?, ?, count(*), ?, ?, ?, ?, ? FROM config_info WHERE tenant_id=?",
				paramList);
	}

	@Override
	public String getDataSource() {
		return DataSourceConstant.DM;
	}

}
