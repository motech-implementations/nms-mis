(function(){
	var nmsReportsApp = angular
		.module('nmsReports')
		.factory('exportUiGridService', exportUiGridService);

            exportUiGridService.inject = ['uiGridExporterService'];
            function exportUiGridService(uiGridExporterService) {
                var service = {
                    exportToExcel: exportToExcel
                };

                return service;

                function Workbook() {
                    if (!(this instanceof Workbook)) return new Workbook();
                    this.SheetNames = [];
                    this.Sheets = {};
                }

                function exportToExcel(sheetName, gridApi,gridApi1, rowTypes, colTypes, excelHeaderName) {
                    var columns = gridApi.grid.options.showHeader ? uiGridExporterService.getColumnHeaders(gridApi.grid, colTypes) : [];
                    var data = uiGridExporterService.getData(gridApi.grid, rowTypes, colTypes);
                    if(excelHeaderName.reportName == "Kilkari Message Matrix" || excelHeaderName.reportName == "Kilkari Repeat Listener"){
                        var columns1 = gridApi1.grid.options.showHeader ? uiGridExporterService.getColumnHeaders(gridApi1.grid, colTypes) : [];
                        var data1 = uiGridExporterService.getData(gridApi1.grid, rowTypes, colTypes);
                    }
                    var fileName = gridApi.grid.options.exporterExcelFilename ? gridApi.grid.options.exporterExcelFilename : 'dokuman';
                    fileName = fileName.replace("*","");
                    fileName += '.xlsx';
                    var wb = new Workbook(),
                        ws = sheetFromArrayUiGrid(data, columns,data1,columns1,excelHeaderName,gridApi,gridApi1,rowTypes, colTypes);
                    wb.SheetNames.push(sheetName);
                    wb.Sheets[sheetName] = ws;
                    var wbout = XLSX.write(wb, {
                        bookType: 'xlsx',
                        bookSST: true,
                        type: 'binary'
                    });
                    saveAs(new Blob([s2ab(wbout)], {
                        type: 'application/octet-stream'
                    }), fileName);
                }

                function sheetFromArrayUiGrid(data, columns,data1,columns1,excelHeaderName,gridApi,gridApi1,rowTypes, colTypes) {
                    var ws = {
                      B1:{t:'s', v:excelHeaderName.reportName},
                      B3:{t:'s', v:excelHeaderName.stateName},
                      F3:{t:'s', v:excelHeaderName.districtName},
                      A3:{t:'s', v:"State:"},
                      E3:{t:'s', v:"District:"},
                      I3:{t:'s', v:"Block:"},
                      H1:{t:'s', v:"Period:"},
                    "!merges":[
                    				{s:{r:0,c:0},e:{r:1,c:0}}, /* A1:A2 */
                    				{s:{r:0,c:1},e:{r:1,c:5}}, /* B1:F2 */
                    				{s:{r:0,c:7},e:{r:1,c:7}}, /* B1:F2 */
                    				{s:{r:0,c:8},e:{r:1,c:11}}, /* B1:F2 */
                    				{s:{r:2,c:1},e:{r:2,c:3}}, /* B1:F2 */
                    				{s:{r:2,c:5},e:{r:2,c:7}}, /* B1:F2 */
                    				{s:{r:2,c:9},e:{r:2,c:11}}, /* B1:F2 */

                    ]

                    };

                    if(excelHeaderName.reportName == "Kilkari Message Matrix"){

                            var ws = {
                                                  B1:{t:'s', v:excelHeaderName.reportName + " (Kilkari MotherPack Data)"},
                                                  B3:{t:'s', v:excelHeaderName.stateName},
                                                  F3:{t:'s', v:excelHeaderName.districtName},
                                                  A3:{t:'s', v:"State:"},
                                                  E3:{t:'s', v:"District:"},
                                                  I3:{t:'s', v:"Block:"},
                                                  H1:{t:'s', v:"Period:"},
                                                  A15:{t:'s', v:"Report:"},
                                                  B15:{t:'s', v:excelHeaderName.reportName + " (Kilkari ChildPack Data)" },
                                                  B17:{t:'s', v:excelHeaderName.stateName},
                                                  F17:{t:'s', v:excelHeaderName.districtName},
                                                  A17:{t:'s', v:"State:"},
                                                  E17:{t:'s', v:"District:"},
                                                  I17:{t:'s', v:"Block:"},
                                                  H15:{t:'s', v:"Period:"},
                                                "!merges":[
                                                			   {s:{r:0,c:0},e:{r:1,c:0}}, /* A1:A2 */
                                                               {s:{r:0,c:1},e:{r:1,c:5}}, /* B1:F2 */
                                                               {s:{r:0,c:7},e:{r:1,c:7}}, /* B1:F2 */
                                                               {s:{r:0,c:8},e:{r:1,c:11}}, /* B1:F2 */
                                                               {s:{r:2,c:1},e:{r:2,c:3}}, /* B1:F2 */
                                                               {s:{r:2,c:5},e:{r:2,c:7}}, /* B1:F2 */
                                                               {s:{r:2,c:9},e:{r:2,c:11}}, /* B1:F2 */
                                                               {s:{r:14,c:0},e:{r:15,c:0}}, /* A1:A2 */
                                                               {s:{r:14,c:1},e:{r:15,c:5}}, /* B1:F2 */
                                                               {s:{r:14,c:7},e:{r:15,c:7}}, /* B1:F2 */
                                                               {s:{r:14,c:8},e:{r:15,c:11}}, /* B1:F2 */
                                                               {s:{r:16,c:1},e:{r:16,c:3}}, /* B1:F2 */
                                                               {s:{r:16,c:5},e:{r:16,c:7}}, /* B1:F2 */
                                                               {s:{r:16,c:9},e:{r:16,c:11}}

                                                ]

                            };

                    }

                    if(excelHeaderName.reportName == "Kilkari Repeat Listener"){

                            var ws = {
                                                  B1:{t:'s', v:excelHeaderName.reportName + " (Beneficiary Count)"},
                                                  B3:{t:'s', v:excelHeaderName.stateName},
                                                  F3:{t:'s', v:excelHeaderName.districtName},
                                                  A3:{t:'s', v:"State:"},
                                                  E3:{t:'s', v:"District:"},
                                                  I3:{t:'s', v:"Block:"},
                                                  H1:{t:'s', v:"Period:"},
                                                  A15:{t:'s', v:"Report:"},
                                                  B15:{t:'s', v:excelHeaderName.reportName + " (Beneficiary Percentage)" },
                                                  B17:{t:'s', v:excelHeaderName.stateName},
                                                  F17:{t:'s', v:excelHeaderName.districtName},
                                                  A17:{t:'s', v:"State:"},
                                                  E17:{t:'s', v:"District:"},
                                                  I17:{t:'s', v:"Block:"},
                                                  H15:{t:'s', v:"Period:"},
                                                "!merges":[
                                                			   {s:{r:0,c:0},e:{r:1,c:0}}, /* A1:A2 */
                                                               {s:{r:0,c:1},e:{r:1,c:5}}, /* B1:F2 */
                                                               {s:{r:0,c:7},e:{r:1,c:7}}, /* B1:F2 */
                                                               {s:{r:0,c:8},e:{r:1,c:11}}, /* B1:F2 */
                                                               {s:{r:2,c:1},e:{r:2,c:3}}, /* B1:F2 */
                                                               {s:{r:2,c:5},e:{r:2,c:7}}, /* B1:F2 */
                                                               {s:{r:2,c:9},e:{r:2,c:11}}, /* B1:F2 */
                                                               {s:{r:14,c:0},e:{r:15,c:0}}, /* A1:A2 */
                                                               {s:{r:14,c:1},e:{r:15,c:5}}, /* B1:F2 */
                                                               {s:{r:14,c:7},e:{r:15,c:7}}, /* B1:F2 */
                                                               {s:{r:14,c:8},e:{r:15,c:11}}, /* B1:F2 */
                                                               {s:{r:16,c:1},e:{r:16,c:3}}, /* B1:F2 */
                                                               {s:{r:16,c:5},e:{r:16,c:7}}, /* B1:F2 */
                                                               {s:{r:16,c:9},e:{r:16,c:11}}

                                                ]

                            };

                    }



                    var range = {
                        s: {
                            c: 10000000,
                            r: 10000000
                        },
                        e: {
                            c: 0,
                            r: 0
                        }
                    };
                    var C = 0;
                    addCell(range, "Report :" , 0, 0, ws);
                    addCell(range, excelHeaderName.timePeriod, 0, 8, ws);
                    addCell(range, excelHeaderName.blockName, 2, 9, ws);
                    if(excelHeaderName.reportName == "Kilkari Message Matrix" || excelHeaderName.reportName == "Kilkari Repeat Listener"){
                        addCell(range, excelHeaderName.timePeriod, 14, 8, ws);
                        addCell(range, excelHeaderName.blockName, 16, 9, ws);
                    }



                    columns.forEach(function (c) {
                        var v = c.displayName || c.value || columns[i].name;
                        addCell(range, v, 4, C, ws);
                        C++;
                    }, this);
                    var R = 5;
                    data.forEach(function (ds) {
                        C = 0;
                        ds.forEach(function (d) {
                            var v = d.value;
                            addCell(range, v, R, C, ws);
                            C++;
                        });
                        R++;
                    }, this);
                    C = 0;
                    var v;
                    if(excelHeaderName.reportName != "Kilkari Message Matrix" && excelHeaderName.reportName != "Kilkari Listening Matrix" && excelHeaderName.reportName != "Kilkari Repeat Listener"){
                        gridApi.grid.columns.forEach(function (ft) {

                           if(ft.displayName == "State" || ft.displayName == "District" || ft.displayName == "Block" || ft.displayName == "Subcenter" || ft.displayName == "Message Number (Week)" )
                               v = "Total";

                           else if(ft.displayName == "Average Duration Of Call" && excelHeaderName.reportName == "Kilkari Cumulative Summary"){
                               var temp = gridApi.grid.columns[3].getAggregationValue()==0?0.00: (gridApi.grid.columns[4].getAggregationValue()/gridApi.grid.columns[3].getAggregationValue());
                               v = Number(temp.toFixed(2));
                           }
                           else if(ft.displayName == "Average Number of Weeks in Service" && excelHeaderName.reportName == "Kilkari Beneficiary Completion"){
                               var temp = gridApi.grid.columns.length==0?0.00: (gridApi.grid.columns[3].getAggregationValue()/gridApi.grid.columns.length);
                               v = Number(temp.toFixed(2));
                           }
                           else if(ft.displayName == "Average Duration Of Calls" && excelHeaderName.reportName == "Kilkari Call"){
                               var temp = gridApi.grid.columns[3].getAggregationValue()==0?0.00: (gridApi.grid.columns[8].getAggregationValue()/gridApi.grid.columns[3].getAggregationValue());
                               v = Number(temp.toFixed(2));
                           }
                           else if(ft.displayName == "% Not Started Course" && excelHeaderName.reportName == "MA Cumulative Summary"){
                              var temp = gridApi.grid.columns[2].getAggregationValue()==0?0.00: (gridApi.grid.columns[4].getAggregationValue()/gridApi.grid.columns[2].getAggregationValue())*100;
                              v = Number(temp.toFixed(2));
                           }
                           else if(ft.displayName == "% Successfully Completed" && excelHeaderName.reportName == "MA Cumulative Summary"){
                              var temp = gridApi.grid.columns[3].getAggregationValue()==0?0.00:(gridApi.grid.columns[5].getAggregationValue()/gridApi.grid.columns[3].getAggregationValue())*100;
                              v = Number(temp.toFixed(2));
                           }
                           else if(ft.displayName == "% Failed the course" && excelHeaderName.reportName == "MA Cumulative Summary"){
                              var temp = gridApi.grid.columns[3].getAggregationValue()==0?0.00:(gridApi.grid.columns[6].getAggregationValue()/gridApi.grid.columns[3].getAggregationValue())*100;
                              v = Number(temp.toFixed(2));
                           }
                           else{
                               v = ft.getAggregationValue();
                           }

                           if(ft.displayName != "S No."){
                                addCell(range, v, R, C, ws);
                                C++;
                           }

                       }, this);

                    }
                        C=0;
                    if(excelHeaderName.reportName == "Kilkari Message Matrix" ||  excelHeaderName.reportName == "Kilkari Repeat Listener"){
                            columns1.forEach(function (c) {
                                var v = c.displayName || c.value || columns[i].name;
                                addCell(range, v, 18, C, ws);
                                C++;
                            }, this);
                            var R = 19;
                            data1.forEach(function (ds) {
                                C = 0;
                                ds.forEach(function (d) {
                                    var v = d.value;
                                    addCell(range, v, R, C, ws);
                                    C++;
                                });
                                R++;
                            }, this);

                       }

                    if (range.s.c < 10000000) ws['!ref'] = XLSX.utils.encode_range(range);
                    return ws;
                }
                /**
                 *
                 * @param {*} data
                 * @param {*} columns
                 */

                function datenum(v, date1904) {
                    if (date1904) v += 1462;
                    var epoch = Date.parse(v);
                    return (epoch - new Date(Date.UTC(1899, 11, 30))) / (24 * 60 * 60 * 1000);
                }

                function s2ab(s) {
                    var buf = new ArrayBuffer(s.length);
                    var view = new Uint8Array(buf);
                    for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
                    return buf;
                }

                function addCell(range, value, row, col, ws) {
                    if (range.s.r > row) range.s.r = row;
                    if (range.s.c > col) range.s.c = col;
                    if (range.e.r < row) range.e.r = row;
                    if (range.e.c < col) range.e.c = col;
                    var cell = {
                        v: value
                    };
                    if (cell.v == null) cell.v = '-';
                    var cell_ref = XLSX.utils.encode_cell({
                        c: col,
                        r: row
                    });

                    if (typeof cell.v === 'number') cell.t = 'n';
                    else if (typeof cell.v === 'boolean') cell.t = 'b';
                    else if (cell.v instanceof Date) {
                        cell.t = 'n';
                        cell.z = XLSX.SSF._table[14];
                        cell.v = datenum(cell.v);
                    } else cell.t = 's';

                    ws[cell_ref] = cell;
                }
            }
})()