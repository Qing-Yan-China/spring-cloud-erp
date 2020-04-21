package com.slq.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slq.pojo.common.Unit;
import com.slq.service.common.IUnitService;
import com.slq.util.DefaultResponseData;
import com.slq.util.ResponseData;

@RequestMapping("/api/unit")
@RestController
public class UnitController {

	@Autowired
	private IUnitService unitService;
	
	/***
	 * 查询这个列名对应的单位
	 * @param key
	 * @return
	 */
	@GetMapping("/valueByKey/{table_column}")
	public ResponseData getUnitValueByKey(@PathVariable("table_column")String table_column) {
		List<Unit> units=unitService.getUnitValueByKey(table_column);
		return null;   
	}
}
