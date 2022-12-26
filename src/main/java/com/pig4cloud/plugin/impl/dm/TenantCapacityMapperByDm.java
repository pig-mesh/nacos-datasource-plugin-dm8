package com.pig4cloud.plugin.impl.dm;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.TenantCapacityMapper;
import com.pig4cloud.plugin.constants.DataSourceConstant;

public class TenantCapacityMapperByDm extends DmAbstractMapper implements TenantCapacityMapper {

	@Override
	public String incrementUsageWithDefaultQuotaLimit() {
		return "UPDATE tenant_capacity SET `usage` = `usage` + 1, gmt_modified = ? WHERE ((tenant_id = ? OR tenant_id IS NULL) OR tenant_id IS NULL) AND `usage` <"
				+ " ? AND quota = 0";
	}

	@Override
	public String incrementUsageWithQuotaLimit() {
		return "UPDATE tenant_capacity SET `usage` = `usage` + 1, gmt_modified = ? WHERE (tenant_id = ? OR tenant_id IS NULL) AND `usage` < "
				+ "quota AND quota != 0";
	}

	@Override
	public String incrementUsage() {
		return "UPDATE tenant_capacity SET `usage` = `usage` + 1, gmt_modified = ? WHERE (tenant_id = ? OR tenant_id IS NULL)";
	}

	@Override
	public String decrementUsage() {
		return "UPDATE tenant_capacity SET `usage` = `usage` - 1, gmt_modified = ? WHERE (tenant_id = ? OR tenant_id IS NULL) AND `usage` > 0";
	}

	@Override
	public String correctUsage() {
		return "UPDATE tenant_capacity SET `usage` = (SELECT count(*) FROM config_info WHERE (tenant_id = ? OR tenant_id IS NULL)), "
				+ "gmt_modified = ? WHERE (tenant_id = ? OR tenant_id IS NULL)";
	}

	@Override
	public String getCapacityList4CorrectUsage() {
		return "SELECT id, tenant_id FROM tenant_capacity WHERE id> AND  ROWNUM > ?";
	}

	@Override
	public String insertTenantCapacity() {
		return "INSERT INTO tenant_capacity (tenant_id, quota, `usage`, `max_size`, max_aggr_count, max_aggr_size, "
				+ "gmt_create, gmt_modified) SELECT ?, ?, count(*), ?, ?, ?, ?, ? FROM config_info WHERE tenant_id=? OR tenant_id IS NULL;";
	}

	@Override
	public String getTableName() {
		return TableConstant.TENANT_CAPACITY;
	}

	@Override
	public String getDataSource() {
		return DataSourceConstant.DM;
	}

}
