import React, { useRef, useEffect } from 'react';
import Chart from 'chart.js/auto';

export default function PieChart({ positive, negative }) {
  const chartRef = useRef(null);

  useEffect(() => {
    if (chartRef && chartRef.current && chartRef.current.chartInstance) {
      chartRef.current.chartInstance.destroy();
    }
    
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

    if (chartRef && chartRef.current) {
      chartRef.current.chartInstance = new Chart(chartRef.current, {
        type: 'pie',
        data: data,
        options: options
      });
    }
  }, [positive, negative]);

  return (
    <div>
      <div style={{ maxWidth: '200px', maxHeight: '200px' }}>
        <canvas ref={chartRef} />
      </div>
    </div>
  );
};



