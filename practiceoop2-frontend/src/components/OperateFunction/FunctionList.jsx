import React, { useEffect, useState } from 'react';
import { Table, Button, Form } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import api from '../../api';
import CustomModal from '../Common/CustomModal';

const FunctionList = () => {
    const [functions, setFunctions] = useState([]);
    const [searchName, setSearchName] = useState('');

    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [deleteFuncId, setDeleteFuncId] = useState(null);
    const [deleteFuncName, setDeleteFuncName] = useState('');

    const [showResultModal, setShowResultModal] = useState(false);
    const [resultMessage, setResultMessage] = useState('');

    const fetchFunctions = async (nameParam) => {
        let url = '/functions/list';
        if (nameParam && nameParam.trim() !== '') {
            url += `?name=${encodeURIComponent(nameParam)}`;
        }
        const response = await api.get(url);
        if (response && response.data) {
            setFunctions(response.data);
        } else {
            setFunctions([]);
        }
    };

    useEffect(() => {
        fetchFunctions('');
    }, []);

    const handleSearch = (e) => {
        e.preventDefault();
        fetchFunctions(searchName);
    };

    const handleClearSearch = () => {
        setSearchName('');
        fetchFunctions('');
    };

    const handleDelete = (id, name) => {
        setDeleteFuncId(id);
        setDeleteFuncName(name);
        setShowDeleteModal(true);
    };

    const confirmDelete = async () => {
        if (!deleteFuncId) return;

        try {
            await api.delete(`/functions/${deleteFuncId}`);
            setShowDeleteModal(false);
            setResultMessage(`Функция "${deleteFuncName}" успешно удалена!`);
            setShowResultModal(true);
            fetchFunctions(searchName);
        } catch (err) {
            setShowDeleteModal(false);
            setResultMessage('Не удалось удалить функцию: ' + (err.response?.data || err.message));
            setShowResultModal(true);
        }
    };

    return (
        <div>
            <h2>Список функций</h2>
            <Form onSubmit={handleSearch} className="mb-3">
                <Form.Group controlId="searchName">
                    <Form.Label>Поиск по названию</Form.Label>
                    <Form.Control 
                        type="text" 
                        placeholder="Введите название функции" 
                        value={searchName}
                        onChange={(e) => setSearchName(e.target.value)}
                    />
                </Form.Group>
                <Button variant="primary" type="submit" className="mt-2">Искать</Button>
                <Button variant="secondary" className="mt-2 ms-2" onClick={handleClearSearch}>Сбросить</Button>
            </Form>

            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Название</th>
                        <th>Действия</th>
                    </tr>
                </thead>
                <tbody>
                    {functions.length > 0 ? functions.map(func => (
                        <tr key={func.id}>
                            <td>{func.id}</td>
                            <td>{func.name}</td>
                            <td>
                                <Button variant="info" as={Link} to={`/view-function/${func.id}`} className="me-2">
                                    Просмотр
                                </Button>
                                <Button variant="danger" onClick={() => handleDelete(func.id, func.name)}>
                                    Удалить
                                </Button>
                            </td>
                        </tr>
                    )): (
                        <tr>
                            <td colSpan="3">Функции не найдены</td>
                        </tr>
                    )}
                </tbody>
            </Table>

            <CustomModal
                show={showDeleteModal}
                onHide={() => setShowDeleteModal(false)}
                title="Подтверждение удаления"
                body={`Вы уверены, что хотите удалить функцию "${deleteFuncName}"?`}
                confirmText="Удалить"
                cancelText="Отмена"
                variant="danger"
                onConfirm={confirmDelete}
            />

            <CustomModal
                show={showResultModal}
                onHide={() => setShowResultModal(false)}
                title="Результат"
                body={resultMessage}
                confirmText="ОК"
            />
        </div>
    );
};

export default FunctionList;
