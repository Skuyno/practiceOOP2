import React, { useEffect, useState } from 'react';
import api from '../../api';
import { Line } from 'react-chartjs-2';
import { Card } from 'react-bootstrap';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title, Tooltip, Legend
} from 'chart.js';
import { useParams } from 'react-router-dom';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const ViewFunction = () => {
    const { id } = useParams();
    const [functionData, setFunctionData] = useState(null);
    const [points, setPoints] = useState([]);

    useEffect(() => {
        const fetchFunction = async () => {
            const response = await api.get(`/functions/${id}`);
            setFunctionData(response.data);
        };
        const fetchPoints = async () => {
            const resp = await api.get(`/points/functions/${id}`);
            const sortedPoints = resp.data.sort((a, b) => a.x - b.x);
            setPoints(sortedPoints);
        };
        fetchFunction();
        fetchPoints();
    }, [id]);

    const data = {
        labels: points.map(p => p.x),
        datasets: [
            {
                label: functionData ? functionData.name : '',
                data: points.map(p => p.y),
                fill: false,
                borderColor: 'rgba(75,192,192,1)'
            }
        ]
    };

    return (
        <Card className="mt-3">
            <Card.Body>
                <h2>{functionData?.name}</h2>
                <Line data={data} />
            </Card.Body>
        </Card>
    );
};

export default ViewFunction;
