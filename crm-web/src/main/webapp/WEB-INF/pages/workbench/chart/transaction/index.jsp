<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <head>
        <script type="text/javascript" src="/jquery/jquery-1.11.1-min.js"></script>
        <script type="text/javascript" src="/jquery/echars/echarts.min.js"></script>
        <script type="text/javascript">
$(function(){
            var chartDom=document.getElementById("main");
            var myChart=echarts.init(chartDom);
            $.ajax({
                'url':"/chart/tran/getData",
                'type':"post",
                'dataType':"json",
                'success':function(data){
                    if(data.code==1){
                    var legendData=new Array(data.object.size);
                    var totalCount=0;
                    $.each(data.object,function(index,obj){
                        totalCount+=parseInt(obj.value);
                    });
                    var funnelData=new Array(data.object.size);
                    $.each(data.object,function(index,obj){
                        legendData[index]=obj.name;

                        funnelData[index]=obj;
                        funnelData[index].value=parseInt(funnelData[index].value)/totalCount*100;
                    });
                    var option = {
                      title: {
                        text: '交易数据漏斗图'
                      },
                      tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c}%'
                      },
                      toolbox: {
                        feature: {
                          dataView: { readOnly: false },
                          restore: {},
                          saveAsImage: {}
                        }
                      },
                      legend: {
                        data: legendData
                      },
                      series: [
                        {
                          name: 'Funnel',
                          type: 'funnel',
                          left: '10%',
                          top: 60,
                          bottom: 60,
                          width: '80%',
                          min: 0,
                          max: 100,
                          minSize: '0%',
                          maxSize: '100%',
                          sort: 'descending',
                          gap: 2,
                          label: {
                            show: true,
                            position: 'inside'
                          },
                          labelLine: {
                            length: 10,
                            lineStyle: {
                              width: 1,
                              type: 'solid'
                            }
                          },
                          itemStyle: {
                            borderColor: '#fff',
                            borderWidth: 1
                          },
                          emphasis: {
                            label: {
                              fontSize: 20
                            }
                          },
                          data: funnelData
                        }
                      ]
                    };

                       option && myChart.setOption(option);
                    }else{
                        alert(data.message);
                    }
                }
            });

        });
        </script>

    </head>
    <body>
        <div id="main" style="height:1000px;width:1200px"></div>
    </body>
</html>