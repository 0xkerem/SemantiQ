import React, { useRef, useEffect } from 'react';
import { Chart } from 'chart.js/auto';
import 'chartjs-adapter-moment';

export default function LineChart({ data }) {
  const chartRef = useRef(null);

  useEffect(() => {
    if (chartRef && chartRef.current && chartRef.current.chartInstance) {
      chartRef.current.chartInstance.destroy();
    }

    const gradient = chartRef.current?.getContext('2d').createLinearGradient(0, 0, 0, 225);
    gradient.addColorStop(0, 'rgba(88, 80, 236, 1)');
    gradient.addColorStop(1, 'rgba(255, 255, 255, 0)'); 

    const chartData = {
      labels: data.map((item) => item.date), 
      datasets: [{
        label: 'Total Usage',
        data: data.map((item) => item.totalUsage), 
        fill: {
          target: 'origin',
          above: gradient,
        },
        borderColor: 'rgba(88, 80, 236, 1)',
        tension: 0.6,
      }]
    };

    const options = {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        x: {
          type: 'time',
          time: {
            unit: 'day'
          },
          grid: {
            display: false,
          }
        },
        y: {
          title: {
            display: true,
          },
          grid: {
            display: false, // Hide vertical grid lines
          },
        }
      }
    };

    if (chartRef && chartRef.current) {
      chartRef.current.chartInstance = new Chart(chartRef.current, {
        type: 'line',
        data: chartData,
        options: options
      });
    }
  }, [data]);

  return (
    <div style={{ height: '240px' }}>
      <div style={{ maxWidth: '700px', height: '100%' }}>
        <canvas ref={chartRef} />
      </div>
    </div>
  );
};
