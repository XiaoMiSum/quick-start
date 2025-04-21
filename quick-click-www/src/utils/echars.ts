// 获取 质量柱状图
interface Title {
  text: string
  subtext?: string
  x?: string
}

export const bar = (
  title: Title,
  xdata: string[],
  data: number[],
  formatter = '{c}%',
  color = true
) => {
  return {
    title: title,
    xAxis: { data: xdata },
    legend: {},
    yAxis: {},
    series: [
      {
        type: 'bar',
        data: () => {
          const results = [] as any[]
          data.forEach((val, idx, array) => {
            // val: 当前值 idx：当前index array: Array
            const item = {
              value: val,
              itemStyle: {
                color: color
                  ? idx === data.length - 1 && data.length > 3 // 标记最后一名
                    ? '#F56C6C'
                    : idx == 0 // 标记第一名
                      ? '#67C23A'
                      : '#409EFF'
                  : '#409EFF'
              }
            }
            results.push(item)
          })
          return results
        },
        label: {
          normal: {
            show: true, // 开启显示
            position: 'top', // 在上方显示
            formatter: formatter // 显示百分号
          }
        }
      }
    ]
  }
}

export const line = (title: Title, xdata: string[], data: number[]) => {
  return {
    title: title,
    xAxis: { type: 'category', data: xdata },
    legend: {},

    yAxis: {},
    series: [
      {
        data: data,
        type: 'line',
        symbol: 'circle', // 实心圆点
        smooth: 0.5, // 设置折线弧度
        label: {
          normal: {
            show: true, // 开启显示
            position: 'top', // 在上方显示
            formatter: '{c}%' // 显示百分号
          }
        }
      }
    ]
  }
}
