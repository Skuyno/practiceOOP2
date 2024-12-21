import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';

const NavigationBar = ({ isAuthenticated, onLogout }) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        onLogout();
        navigate('/login');
    };

    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Container>
                <Navbar.Brand as={Link} to="/">PracticeOOP2</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        {isAuthenticated && (
                            <>
                                <Nav.Link as={Link} to="/create-function">Создать Функцию</Nav.Link>
                                <Nav.Link as={Link} to="/functions">Список Функций</Nav.Link>
                                <Nav.Link as={Link} to="/operate-functions">Операции над функциями</Nav.Link>
                            </>
                        )}
                    </Nav>
                    <Nav>
                        {isAuthenticated ? (
                            <Nav.Link onClick={handleLogout}>Выйти</Nav.Link>
                        ) : (
                            <>
                                <Nav.Link as={Link} to="/login">Войти</Nav.Link>
                                <Nav.Link as={Link} to="/register">Зарегистрироваться</Nav.Link>
                            </>
                        )}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default NavigationBar;
