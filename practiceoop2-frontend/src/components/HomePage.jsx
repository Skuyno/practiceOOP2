import React from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const HomePage = () => {
    return (
        <Container className="mt-5">
            {/* Hero Section */}
            <Row className="mb-5">
                <Col>
                    <div className="p-5 bg-light rounded-3">
                        <h1 className="display-4">Добро пожаловать!</h1>
                        <p className="lead">
                            Это наше приложение для работы с табулированными функциями.
                            Здесь вы можете создавать функции, просматривать их графики,
                            выполнять операции над ними и многое другое.
                        </p>
                        <hr className="my-4" />
                        <p>Воспользуйтесь возможностями приложения уже сейчас!</p>
                        <Button as={Link} to="/functions" variant="primary" size="lg">
                            Перейти к списку функций
                        </Button>
                    </div>
                </Col>
            </Row>

            {/* Cards Section */}
            <Row>
                <Col md={4}>
                    <Card className="mb-4 shadow-sm">
                        <Card.Body>
                            <Card.Title>Создание функций</Card.Title>
                            <Card.Text>
                                Введите данные точек вашей функции и сохраните её в базе данных.
                                Затем вы сможете просмотреть её график.
                            </Card.Text>
                            <Button as={Link} to="/create-function" variant="success">
                                Создать функцию
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="mb-4 shadow-sm">
                        <Card.Body>
                            <Card.Title>Список функций</Card.Title>
                            <Card.Text>
                                Просматривайте уже созданные функции, фильтруйте их по названию,
                                удаляйте ненужные и открывайте для просмотра графиков.
                            </Card.Text>
                            <Button as={Link} to="/functions" variant="primary">
                                Посмотреть список
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="mb-4 shadow-sm">
                        <Card.Body>
                            <Card.Title>Операции над функциями</Card.Title>
                            <Card.Text>
                                Выполняйте арифметические операции (сложение, вычитание, умножение, деление)
                                над двумя существующими функциями и сохраняйте результат.
                            </Card.Text>
                            <Button as={Link} to="/operate-functions" variant="warning">
                                Перейти к операциям
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default HomePage;
