import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';

export default function PieChart({ positive, negative }) {
  const chartContainerRef = useRef(null);
  const chartRef = useRef(null);

  useEffect(() => {
    let chartInstance = null;

    const resizeHandler = () => {
      if (chartInstance) {
        chartInstance.destroy();
        chartInstance = null;
      }

      const parent = chartContainerRef.current;
      const width = parent.clientWidth;
      const height = parent.clientHeight;

      const ctx = chartRef.current.getContext('2d');

      const data = {
        labels: ["Positive", "Negative"],
        datasets: [{
          data: [Math.abs(positive), Math.abs(negative)],
          backgroundColor: [
            'rgba(104, 110, 189, 1)',
            'rgba(147, 154, 223, 1)',
          ],
          borderWidth: 1
        }]
      };

      const options = {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false // Hide the legend
          }
        }
      };

      chartRef.current.width = width;
      chartRef.current.height = height;

      chartInstance = new Chart(ctx, {
        type: 'pie',
        data: data,
        options: options
      });
    };

    resizeHandler(); // Initial resize

    window.addEventListener('resize', resizeHandler);

    return () => {
      window.removeEventListener('resize', resizeHandler);

      if (chartInstance) {
        chartInstance.destroy();
      }
    };
  }, [positive, negative]);

  return (
    <div ref={chartContainerRef} style={{ width: '100%', height: '100%' }}>
      <canvas ref={chartRef} style={{ width: '100%', height: '100%' }} />
    </div>
  );
}
