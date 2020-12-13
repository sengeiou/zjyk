$(window).resize(function () {
    console.log('resize');
});

$(document).ready(function () {
    console.log('ready');
    loadBaseInfo();
    loadCodeLossScore();
    loadTypeLossScore();
    loadPointLossScore();
    loadDifficultyLossScore();
    loadErrorTypeLossScore();
});

function loadBaseInfo() {
    document.title = record.studentName + '(考情分析)';
    $('#studentName').text(record.studentName + '(考情分析)');
    $('#paperName').text(record.paper.name);

    $('#noteAll').text(record.noteAll);
    $('#noteTypeLossScore').text(record.noteType);
    $('#notePointLossScore').text(record.notePoint);
    $('#noteDifficultyLossScore').text(record.noteDifficulty);
    $('#noteErrorTypeLossScore').text(record.noteErrorType);
    $('#noteOffer').text(record.noteOffer);
}


function loadCodeLossScore() {

    var categories = [];
    var data = [];

    for (var i = 0; i < codeLossScoreList.length; i++) {
        var codeLossScore = codeLossScoreList[i];
        categories[categories.length] = codeLossScore.code;
        data[data.length] = codeLossScore.gotLv;
    }

    var title = {
        text: '得分率',
        style: {
            fontSize: '36px',
            color: '#998bfd'
        }
    };
    var xAxis = {
        categories: categories,
        labels: {
            style: {
                fontSize: '26px',
                color: '#998bfd'
            }
        }
    };
    var yAxis = {
        min: 0,
        title: {
            enabled: false
        },
        labels: {
            style: {
                fontSize: '26px',
                color: '#998bfd'
            }
        }
    };
    var tooltip = {
        enabled: false,
        valueSuffix: ' 分'
    };
    var plotOptions = {
        bar: {
            dataLabels: {
                enabled: true,
                formatter: function () {
                    return this.y + ' 分';
                },
                style: {
                    fontSize: '26px',
                    color: '#998bfd'
                }
            }
        }
    };
    var legend = {
        enabled: false
    };
    var credits = {
        enabled: false
    };
    var series = [{
        name: '失分',
        data: data,
        color: '#998bfd'
    }];
    var json = {};
    json.title = title;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.tooltip = tooltip;
    json.plotOptions = plotOptions;
    json.legend = legend;
    json.credits = credits;
    json.series = series;
    $('#containerCodeLossScore').highcharts(json);
}


function loadTypeLossScore() {

    var categories = [];
    var lossData = [];
    var gotData = [];

    for (var i = 0; i < typeLossScoreList.length; i++) {
        var typeLossScore = typeLossScoreList[i];
        if (typeLossScore.type == 0) {
            categories[categories.length] = '单选题';
            lossData[lossData.length] = typeLossScore.lossScore;
            gotData[gotData.length] = typeLossScore.allScore - typeLossScore.lossScore;
        } else if (typeLossScore.type == 1) {
            categories[categories.length] = '多选题';
            lossData[lossData.length] = typeLossScore.lossScore;
            gotData[gotData.length] = typeLossScore.allScore - typeLossScore.lossScore;
        } else if (typeLossScore.type == 2) {
            categories[categories.length] = '填空题';
            lossData[lossData.length] = typeLossScore.lossScore;
            gotData[gotData.length] = typeLossScore.allScore - typeLossScore.lossScore;
        } else if (typeLossScore.type == 3) {
            categories[categories.length] = '解答题';
            lossData[lossData.length] = typeLossScore.lossScore;
            gotData[gotData.length] = typeLossScore.allScore - typeLossScore.lossScore;
        }
    }

    var chart = {
        type: 'bar'
    };
    var title = {
        text: '题型得分',
        style: {
            fontSize: '36px',
            color: '#fb795f'
        }
    };
    var xAxis = {
        categories: categories,
        labels: {
            style: {
                fontSize: '26px',
                color: '#fb795f'
            }
        }
    };
    var yAxis = {
        min: 0,
        title: {
            enabled: false
        },
        labels: {
            style: {
                fontSize: '26px',
                color: '#fb795f'
            }
        }
    };
    var tooltip = {
        enabled: false,
        valueSuffix: ' 分'
    };
    var plotOptions = {
        bar: {
            dataLabels: {
                enabled: true,
                formatter: function () {
                    return this.y + ' 分';
                },
                style: {
                    fontSize: '26px',
                    color: '#fb795f'
                }
            }
        },
        series: {
            stacking: 'normal'
        }
    };
    var legend = {
        enabled: false
    };
    var credits = {
        enabled: false
    };
    var series = [{
        name: '失分',
        data: lossData,
        color: '#fcc4b6'
    }, {
        name: '得分',
        data: gotData,
        color: '#f97858'
    }];
    var json = {};
    json.chart = chart;
    json.title = title;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.tooltip = tooltip;
    json.plotOptions = plotOptions;
    json.legend = legend;
    json.credits = credits;
    json.series = series;
    $('#containerTypeLossScore').css('height', 116 + typeLossScoreList.length * 100 + 'px');
    $('#containerTypeLossScore').highcharts(json);
}

function loadPointLossScore() {

    var categories = [];
    var data = [];

    for (var i = 0; i < pointLossScoreList.length; i++) {
        var pointLossScore = pointLossScoreList[i];
        var category = pointLossScore.pointName;
        if (category.length > 15) {
            var pos = category.length / 2;
            category = category.slice(0, pos) + '<br>' + category.slice(pos);
        }
        categories[categories.length] = category;
        data[data.length] = pointLossScore.lossScore;
    }

    var chart = {
        type: 'bar'
    };
    var title = {
        text: '考点失分',
        style: {
            fontSize: '36px',
            color: '#998bfd'
        }
    };
    var xAxis = {
        categories: categories,
        labels: {
            style: {
                fontSize: '26px',
                color: '#998bfd'
            }
        }
    };
    var yAxis = {
        min: 0,
        title: {
            enabled: false
        },
        labels: {
            style: {
                fontSize: '26px',
                color: '#998bfd'
            }
        }
    };
    var tooltip = {
        enabled: false,
        valueSuffix: ' 分'
    };
    var plotOptions = {
        bar: {
            dataLabels: {
                enabled: true,
                formatter: function () {
                    return this.y + ' 分';
                },
                style: {
                    fontSize: '26px',
                    color: '#998bfd'
                }
            }
        }
    };
    var legend = {
        enabled: false
    };
    var credits = {
        enabled: false
    };
    var series = [{
        name: '失分',
        data: data,
        color: '#998bfd'
    }];
    var json = {};
    json.chart = chart;
    json.title = title;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.tooltip = tooltip;
    json.plotOptions = plotOptions;
    json.legend = legend;
    json.credits = credits;
    json.series = series;
    $('#containerPointLossScore').css('height', 116 + pointLossScoreList.length * 100 + 'px');
    $('#containerPointLossScore').highcharts(json);
}


function loadDifficultyLossScore() {

    var categories = [];
    var lossData = [];
    var gotData = [];

    for (var i = 0; i < difficultyLossScoreList.length; i++) {
        var difficultyLossScore = difficultyLossScoreList[i];
        if (difficultyLossScore.difficulty == 1) {
            categories[categories.length] = '简单';
            lossData[lossData.length] = difficultyLossScore.lossScore;
            gotData[gotData.length] = difficultyLossScore.allScore - difficultyLossScore.lossScore;
        } else if (difficultyLossScore.difficulty == 2) {
            categories[categories.length] = '一般';
            lossData[lossData.length] = difficultyLossScore.lossScore;
            gotData[gotData.length] = difficultyLossScore.allScore - difficultyLossScore.lossScore;
        } else if (difficultyLossScore.difficulty == 3) {
            categories[categories.length] = '困难';
            lossData[lossData.length] = difficultyLossScore.lossScore;
            gotData[gotData.length] = difficultyLossScore.allScore - difficultyLossScore.lossScore;
        }
    }

    var chart = {
        type: 'bar'
    };
    var title = {
        text: '难度得分',
        style: {
            fontSize: '36px',
            color: '#fb795f'
        }
    };
    var xAxis = {
        categories: categories,
        labels: {
            style: {
                fontSize: '26px',
                color: '#fb795f'
            }
        }
    };
    var yAxis = {
        min: 0,
        title: {
            enabled: false
        },
        labels: {
            style: {
                fontSize: '26px',
                color: '#fb795f'
            }
        }
    };
    var tooltip = {
        enabled: false,
        valueSuffix: ' 分'
    };
    var plotOptions = {
        bar: {
            dataLabels: {
                enabled: true,
                formatter: function () {
                    return this.y + ' 分';
                },
                style: {
                    fontSize: '26px',
                    color: '#fb795f'
                }
            }
        },
        series: {
            stacking: 'normal'
        }
    };
    var legend = {
        enabled: false
    };
    var credits = {
        enabled: false
    };
    var series = [{
        name: '失分',
        data: lossData,
        color: '#fcc4b6'
    }, {
        name: '得分',
        data: gotData,
        color: '#f97858'
    }];
    var json = {};
    json.chart = chart;
    json.title = title;
    json.xAxis = xAxis;
    json.yAxis = yAxis;
    json.tooltip = tooltip;
    json.plotOptions = plotOptions;
    json.legend = legend;
    json.credits = credits;
    json.series = series;
    $('#containerDifficultyLossScore').css('height', 116 + difficultyLossScoreList.length * 100 + 'px');
    $('#containerDifficultyLossScore').highcharts(json);
}


function loadErrorTypeLossScore() {

    var data = [];

    for (var i = 0; i < errorTypeLossScoreList.length; i++) {
        var errorTypeLossScore = errorTypeLossScoreList[i];
        if (errorTypeLossScore.errorType == 0) {
            data[data.length] = {
                name: 'A类',
                y: errorTypeLossScore.lossScore,
                color: 'chocolate'
            };
        } else if (errorTypeLossScore.errorType == 1) {
            data[data.length] = {
                name: 'B类',
                y: errorTypeLossScore.lossScore,
                color: 'blueviolet'
            };
        } else if (errorTypeLossScore.errorType == 2) {
            data[data.length] = {
                name: 'C类',
                y: errorTypeLossScore.lossScore,
                color: 'deeppink'
            };
        } else if (errorTypeLossScore.errorType == 3) {
            data[data.length] = {
                name: 'D类',
                y: errorTypeLossScore.lossScore,
                color: 'darkcyan'
            };
        }
    }

    var chart = {
        type: 'pie'
    };
    var title = {
        text: '错误类型',
        style: {
            fontSize: '36px',
            color: '#6ec703'
        }
    };
    var tooltip = {
        enabled: false,
        pointFormat: '丢失 {point.y} 分'
    };
    var plotOptions = {
        pie: {
            dataLabels: {
                enabled: true,
                format: '【 {point.name} 】丢失 {point.y} 分',
                style: {
                    fontSize: '26px',
                    color: '#6ec703'
                }
            }
        }
    };
    var legend = {
        enabled: false
    };
    var credits = {
        enabled: false
    };
    var series = [{
        data: data,
        innerSize: '60%'
    }];
    var json = {};
    json.chart = chart;
    json.title = title;
    json.tooltip = tooltip;
    json.plotOptions = plotOptions;
    json.legend = legend;
    json.credits = credits;
    json.series = series;
    $('#containerErrorTypeLossScore').highcharts(json);
}