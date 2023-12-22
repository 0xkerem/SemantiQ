import React, { useEffect, useRef, useState } from 'react';
import { Chart } from 'chart.js/auto';
import 'chartjs-adapter-moment';

export default function LineChart({ data }) {
  const chartContainerRef = useRef(null);
  const chartRef = useRef(null);
  const [containerSize, setContainerSize] = useState({ width: 0, height: 240 });

  useEffect(() => {
    const updateContainerSize = () => {
      if (chartContainerRef.current) {
        const width = chartContainerRef.current.clientWidth;
        const height = chartContainerRef.current.clientHeight;
        setContainerSize({ width, height });
      }
    };

    updateContainerSize();
    window.addEventListener('resize', updateContainerSize);

    return () => {
      window.removeEventListener('resize', updateContainerSize);
    };
  }, []);

  useEffect(() => {
    if (chartRef && chartRef.current && chartRef.current.chartInstance) {
      chartRef.current.chartInstance.destroy();
    }

    const ctx = chartRef.current.getContext('2d');

    const gradient = ctx.createLinearGradient(0, 0, 0, containerSize.height);
    gradient.addColorStop(0, 'rgba(88, 80, 236, 1)');
    gradient.addColorStop(1, 'rgba(255, 255, 255, 0)');

    const lastTwelveDaysData = data.slice(-12); // Take the last 12 days of data

    const chartData = {
      labels: lastTwelveDaysData.map((item) => item.date),
      datasets: [{
        label: 'Total Usage',
        data: lastTwelveDaysData.map((item) => item.totalUsage),
        fill: {
          target: 'origin',
          above: gradient,
        },
        borderColor: 'rgba(88, 80, 236, 1)',
        tension: 0.6,
      }],
    };

    const options = {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        x: {
          type: 'time',
          time: {
            unit: 'day',
          },
          grid: {
            display: false,
          },
          max: data.length > 12 ? data[data.length - 1].date : undefined, // Set max date to the last date in the provided data
        },
        y: {
          title: {
            display: true,
          },
          grid: {
            display: false,
          },
        },
      },
    };

    const chartInstance = new Chart(ctx, {
      type: 'line',
      data: chartData,
      options: options,
    });

    chartRef.current.chartInstance = chartInstance;

    return () => {
      if (chartInstance) {
        chartInstance.destroy();
      }
    };
  }, [data, containerSize]);

  return (
    <div ref={chartContainerRef} style={{ width: '100%', display: 'flex', justifyContent: 'center' }}>
      <canvas ref={chartRef} width={containerSize.width} height={containerSize.height} />
    </div>
  );
}
