import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import FunctionForm from './FunctionForm';
import FunctionTable from './FunctionTable';
import api from '../../api';
import CustomModal from '../Common/CustomModal';

const CreateFunction = () => {
    const [numPoints, setNumPoints] = useState(0);
    const [points, setPoints] = useState([]);
    const [functionName, setFunctionName] = useState('');
    const [showTable, setShowTable] = useState(false);
    const [error, setError] = useState(null);

    const [showErrorModal, setShowErrorModal] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [showSuccessModal, setShowSuccessModal] = useState(false);

    const handleSetFunctionData = ({ functionName, number }) => {
        setFunctionName(functionName);
        setNumPoints(number);
        setPoints(Array.from({ length: number }, () => ({ x: '', y: '' })));
        setShowTable(true);
    };

    const handleChange = (index, field, value) => {
        const updatedPoints = [...points];
        updatedPoints[index][field] = value;
        setPoints(updatedPoints);
    };

    const handleSubmit = async () => {
        try {
            const xValues = points.map(p => parseFloat(p.x));
            const yValues = points.map(p => parseFloat(p.y));

            if (xValues.some(isNaN) || yValues.some(isNaN)) {
                throw new Error("Все значения x и y должны быть числами.");
            }

            const x_from = Math.min(...xValues);
            const x_to = Math.max(...xValues);

            const mathFunctionData = {
                name: functionName,
                count: numPoints,
                x_from: parseFloat(x_from),
                x_to: parseFloat(x_to),
            };

            const response = await api.post('/functions', mathFunctionData);
            const functionId = response.data.id;

            for (let i = 0; i < points.length; i++) {
                const pointData = {
                    functionId,
                    x: parseFloat(points[i].x),
                    y: parseFloat(points[i].y),
                };
                await api.post('/points', pointData);
            }

            setSuccessMessage('Табулированная функция успешно создана!');
            setShowSuccessModal(true);

            setShowTable(false);
            setNumPoints(0);
            setPoints([]);
            setFunctionName('');
        } catch (err) {
            setError(err.response?.data || err.message);
            setShowErrorModal(true);
        }
    };

    return (
        <>
            <FunctionForm onSubmit={handleSetFunctionData} />
            {showTable && (
                <>
                    <FunctionTable points={points} onChange={handleChange} />
                    <Button variant="primary" onClick={handleSubmit} className="mt-3">
                        Создать
                    </Button>
                </>
            )}

            <CustomModal
                show={showErrorModal}
                onHide={() => setShowErrorModal(false)}
                title="Ошибка"
                body={error}
                confirmText="Закрыть"
                variant="danger"
            />

            <CustomModal
                show={showSuccessModal}
                onHide={() => setShowSuccessModal(false)}
                title="Успех"
                body={successMessage}
                confirmText="ОК"
                variant="success"
            />
        </>
    );
};

export default CreateFunction;
