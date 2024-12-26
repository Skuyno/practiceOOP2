// src/components/CreateFunction/CreateFunction.js

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

    const [currentPage, setCurrentPage] = useState(0);
    const pointsPerPage = 10;
    const totalPages = Math.ceil(points.length / pointsPerPage);

    const handleSetFunctionData = ({ functionName, number }) => {
        setFunctionName(functionName);
        setNumPoints(number);
        setPoints(Array.from({ length: number }, () => ({ x: '', y: '' })));
        setShowTable(true);
        setCurrentPage(0);
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

            const xSet = new Set(xValues);
            if (xSet.size < xValues.length) {
                throw new Error("В списке есть дублирующиеся X! Убедитесь, что все X уникальны.");
            }
            if (!functionName.trim()) {
                throw new Error("Название функции не может быть пустым.");
            }
            if (numPoints <= 0) {
                throw new Error("Количество точек должно быть > 0.");
            }
            if (xValues.some(isNaN) || yValues.some(isNaN)) {
                throw new Error("Все значения x и y должны быть числами.");
            }

            const x_from = Math.min(...xValues);
            const x_to = Math.max(...xValues);

            if (x_to <= x_from) {
                throw new Error("Диапазон X некорректен (x_to не может быть меньше или равен x_from).");
            }

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
            setCurrentPage(0);
        } catch (err) {
            setError(err.response?.data || err.message);
            setShowErrorModal(true);
        }
    };

    const goToNextPage = () => {
        if (currentPage < totalPages - 1) {
            setCurrentPage(prev => prev + 1);
        }
    };

    const goToPrevPage = () => {
        if (currentPage > 0) {
            setCurrentPage(prev => prev - 1);
        }
    };

    const goToPage = (pageNumber) => {
        if (pageNumber >= 0 && pageNumber < totalPages) {
            setCurrentPage(pageNumber);
        }
    };

    return (
        <>
            <FunctionForm onSubmit={handleSetFunctionData} />
            {showTable && (
                <>
                    <FunctionTable 
                        points={points} 
                        onChange={handleChange} 
                        currentPage={currentPage}
                        pointsPerPage={pointsPerPage}
                    />
                    <div className="d-flex justify-content-center align-items-center mb-3">
                        <Button 
                            variant="secondary" 
                            onClick={goToPrevPage} 
                            disabled={currentPage === 0}
                            className="me-2"
                        >
                            Предыдущая
                        </Button>
                        <span>
                            Страница {currentPage + 1} из {totalPages}
                        </span>
                        <Button 
                            variant="secondary" 
                            onClick={goToNextPage} 
                            disabled={currentPage >= totalPages - 1}
                            className="ms-2"
                        >
                            Следующая
                        </Button>
                    </div>
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
                confirmText="OK"
                variant="success"
            />
        </>
    );
};

export default CreateFunction;
