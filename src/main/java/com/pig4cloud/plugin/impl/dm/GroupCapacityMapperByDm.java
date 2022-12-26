package com.pig4cloud.plugin.impl.dm;

import com.alibaba.nacos.plugin.datasource.constants.TableConstant;
import com.alibaba.nacos.plugin.datasource.mapper.GroupCapacityMapper;
import com.pig4cloud.plugin.constants.DataSourceConstant;

public class GroupCapacityMapperByDm extends DmAbstractMapper implements GroupCapacityMapper {

	@Override
	public String insertIntoSelect() {
		return "INSERT INTO group_capacity (group_id, quota, `usage`, `max_size`, max_aggr_count, max_aggr_size,gmt_create,"
				+ " gmt_modified) SELECT ?, ?, count(*), ?, ?, ?, ?, ? FROM config_info";
	}

	@Override
	public String insertIntoSelectByWhere() {
		return "INSERT INTO group_capacity (group_id, quota,`usage`, `max_size`, max_aggr_count, max_aggr_size, gmt_create,"
				+ " gmt_modified) SELECT ?, ?, count(*), ?, ?, ?, ?, ? FROM config_info WHERE group_id=? AND tenant_id = ''";
	}

	@Override
	public String incrementUsageByWhereQuotaEqualZero() {
		return "UPDATE group_capacity SET `usage` = `usage` + 1, gmt_modified = ? WHERE group_id = ? AND `usage` < ? AND quota = 0";
	}

	@Override
	public String incrementUsageByWhereQuotaNotEqualZero() {
		return "UPDATE group_capacity SET `usage` = `usage` + 1, gmt_modified = ? WHERE group_id = ? AND `usage` < quota AND quota != 0";
	}

	@Override
	public String incrementUsageByWhere() {
		return "UPDATE group_capacity SET `usage` = `usage` + 1, gmt_modified = ? WHERE group_id = ?";
	}

	@Override
	public String decrementUsageByWhere() {
		return "UPDATE group_capacity SET `usage` = `usage` - 1, gmt_modified = ? WHERE group_id = ? AND `usage` > 0";
	}

	@Override
	public String updateUsage() {
		return "UPDATE group_capacity SET `usage` = (SELECT count(*) FROM config_info), gmt_modified = ? WHERE group_id = ?";
	}

	@Override
	public String updateUsageByWhere() {
		return "UPDATE group_capacity SET `usage` = (SELECT count(*) FROM config_info WHERE group_id=? AND tenant_id = ''),"
				+ " gmt_modified = ? WHERE group_id= ?";
	}

	@Override
	public String selectGroupInfoBySize() {
		return "SELECT id, group_id FROM group_capacity WHERE id > ? ROWNUM > ?";
	}

	@Override
	public String getTableName() {
		return TableConstant.GROUP_CAPACITY;
	}

	@Override
	public String getDataSource() {
		return DataSourceConstant.DM;
	}

}
