import React, { useEffect, useState } from 'react';
import { Table, Button, Form } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import api from '../../api';

const FunctionListPaged = () => {
  const [functions, setFunctions] = useState([]);
  const [searchName, setSearchName] = useState('');
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(5);

  const [totalPages, setTotalPages] = useState(1);

  const fetchFunctions = async (nameParam, pageParam, sizeParam) => {
    let url = `/functions/list-paged?page=${pageParam}&size=${sizeParam}`;
    if (nameParam && nameParam.trim() !== '') {
      url += `&name=${encodeURIComponent(nameParam)}`;
    }

    const response = await api.get(url);
    if (response && response.data) {
      const data = response.data;
      setFunctions(data.content);
      setTotalPages(data.totalPages);
    } else {
      setFunctions([]);
      setTotalPages(1);
    }
  };
  useEffect(() => {
    fetchFunctions(searchName, page, size);
  }, [page, size]);

  const handleSearch = (e) => {
    e.preventDefault();
    setPage(0);
    fetchFunctions(searchName, 0, size);
  };

  const handleClearSearch = () => {
    setSearchName('');
    setPage(0);
    fetchFunctions('', 0, size);
  };

  const goToNextPage = () => {
    if (page < totalPages - 1) {
      setPage(page + 1);
    }
  };
  const goToPrevPage = () => {
    if (page > 0) {
      setPage(page - 1);
    }
  };

  const handleDelete = async (id, name) => {
    const confirmed = window.confirm(`Вы действительно хотите удалить функцию "${name}"?`);
    if (!confirmed) return;

    try {
      await api.delete(`/functions/${id}`);
      fetchFunctions(searchName, page, size);
    } catch (err) {
      alert('Ошибка при удалении функции: ' + (err.response?.data || err.message));
    }
  };

  return (
    <div>
      <h2>Список функций</h2>
      <Form onSubmit={handleSearch} className="mb-3">
        <Form.Group controlId="searchName">
          <Form.Control
            type="text"
            placeholder="Введите название функции"
            value={searchName}
            onChange={(e) => setSearchName(e.target.value)}
          />
        </Form.Group>
        <Button variant="primary" type="submit" className="mt-2">
          Искать
        </Button>
        <Button variant="secondary" className="mt-2 ms-2" onClick={handleClearSearch}>
          Сбросить
        </Button>
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
          {functions.length > 0 ? (
            functions.map((func) => (
              <tr key={func.id}>
                <td>{func.id}</td>
                <td>{func.name}</td>
                <td>
                  <Button
                    variant="info"
                    as={Link}
                    to={`/view-function/${func.id}`}
                    className="me-2"
                  >
                    Просмотр
                  </Button>
                  <Button
                    variant="danger"
                    onClick={() => handleDelete(func.id, func.name)}
                  >
                    Удалить
                  </Button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="3">Функции не найдены</td>
            </tr>
          )}
        </tbody>
      </Table>

      <div className="d-flex justify-content-center align-items-center">
        <Button
          variant="secondary"
          onClick={goToPrevPage}
          className="me-3"
          disabled={page === 0}
        >
          Предыдущая
        </Button>
        <span>
          Страница {page + 1} из {totalPages}
        </span>
        <Button
          variant="secondary"
          onClick={goToNextPage}
          className="ms-3"
          disabled={page >= totalPages - 1}
        >
          Следующая
        </Button>
      </div>
    </div>
  );
};

export default FunctionListPaged;
