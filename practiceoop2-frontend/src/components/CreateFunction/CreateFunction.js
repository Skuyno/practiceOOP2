import React, { useState } from 'react';
import FunctionForm from './FunctionForm';
import FunctionTable from './FunctionTable';
import ErrorModal from './ErrorModal';
import api from '../../api';
import { Button, Modal } from 'react-bootstrap';

const CreateFunction = () => {
    const [numPoints, setNumPoints] = useState(0);
    const [points, setPoints] = useState([]);
    const [showTable, setShowTable] = useState(false);
    const [error, setError] = useState(null);
    const [showModal, setShowModal] = useState(false);

    const handleSetNumPoints = (number) => {
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

            const response = await api.post('/functions/tabulated-functions', {
                x: xValues,
                y: yValues
            });

            alert('TabulatedFunction успешно создана!');
            setShowTable(false);
            setNumPoints(0);
            setPoints([]);
        } catch (err) {
            setError(err.response?.data || err.message);
            setShowModal(true);
        }
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    return (
        <>
            <FunctionForm onSubmit={handleSetNumPoints} />
            {showTable && (
                <>
                    <FunctionTable points={points} onChange={handleChange} />
                    <Button variant="primary" onClick={handleSubmit} className="mt-3">
                        Создать
                    </Button>
                </>
            )}
            <Modal show={showModal} onHide={handleCloseModal} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Ошибка</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {error}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={handleCloseModal}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default CreateFunction;
