package cn.bo.project.gen.controller;

import cn.bo.project.gen.Response.ResponseData;
import cn.bo.project.gen.entity.GenTable;
import cn.bo.project.gen.entity.GenTableColumn;
import cn.bo.project.gen.service.GenTableColumnService;
import cn.bo.project.gen.service.GenTableService;
import cn.bo.project.gen.util.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhangbo
 * @Date 2020/7/27 10:32
 * @Description
 * @PackageName cn.bo.project.gen.controller
 **/
@Api(tags = "代码生成Api")
@RestController
@RequestMapping("gen")
public class GenerateController {

    @Autowired
    private GenTableService genTableService;
    @Autowired
    private GenTableColumnService genTableColumnService;

    @PostMapping("/db/list")
    @ApiOperation("查询数据库列表")
    public ResponseData dataList(@RequestBody GenTable genTable) {
        List<GenTable> list = genTableService.selectDbTableList(genTable);
        return ResponseData.success(list);
    }

    @GetMapping(value = "/column/{tableId}")
    @ApiOperation("查询数据表字段列表")
    public ResponseData columnList(@PathVariable Long tableId) {
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        return ResponseData.success(list);
    }

    @GetMapping("/preview/{tableId}")
    @ApiOperation("代码预览")
    public ResponseData preview(@PathVariable("tableId") Long tableId) throws IOException {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return ResponseData.success(dataMap);
    }

    @GetMapping("/genCode/{tableName}")
    @ApiOperation(value = "代码生成",produces = "application/octet-stream")
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genTableService.generatorCode(tableName);
        genCode(response, data);
    }

    @GetMapping("/batchGenCode")
    @ApiOperation("批量代码生成")
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = tables.split(",");
        byte[] data = genTableService.generatorCode(tableNames);
        genCode(response, data);
    }

    @DeleteMapping("/{tableIds}")
    @ApiOperation("删除代码生成")
    public ResponseData remove(@PathVariable Long[] tableIds) {
        genTableService.deleteGenTableByIds(tableIds);
        return ResponseData.success();
    }

    @PutMapping
    @ApiOperation("修改保存代码生成业务")
    public ResponseData editSave(@Validated @RequestBody GenTable genTable) {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return ResponseData.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询代码生成列表")
    public ResponseData genList(GenTable genTable) {
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return ResponseData.success(list);
    }

    @GetMapping(value = "/{tableId}")
    @ApiOperation("修改代码生成业务")
    public ResponseData getInfo(@PathVariable Long tableId) {
        GenTable genTable = genTableService.selectGenTableById(tableId);
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", genTable);
        map.put("rows", list);
        return ResponseData.success(map);
    }

    @PostMapping("/importTable")
    public ResponseData importTableSave(String tables)
    {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList);
        return ResponseData.success();
    }

    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
