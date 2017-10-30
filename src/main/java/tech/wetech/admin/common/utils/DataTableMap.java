package tech.wetech.admin.common.utils;

import tech.wetech.admin.web.dto.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjbi
 */
public class DataTableMap {

	/**
	 * 将数据封装为map，以便传到前台供datatables读取数据；支持后台分页
	 * @param page 分页对象
	 *  json.sEcho Tracking flag for DataTables to match requests
	 *  json.iTotalRecords Number of records in the data set, not accounting for filtering
	 *  json.iTotalDisplayRecords Number of records in the data set, accounting for filtering
	 *  json.aaData The data to display on this page
	 *  [json.sColumns] Column ordering (sName, comma separated)
	 * @return Data the data from the server (nuking the old) and redraw the table
	 */
	public static Map<String, Object> getMapData(Page page) {
		Map<String,Object> map = new HashMap<>();
		map.put("aaData", page.getResult());
		map.put("iTotalRecords", (page.getTotal()));
		map.put("iTotalDisplayRecords", (page.getTotal()));
		return map;
	}

}

