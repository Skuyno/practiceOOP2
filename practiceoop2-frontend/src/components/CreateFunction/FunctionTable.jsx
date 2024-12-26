import React from 'react';
import { Table, Form } from 'react-bootstrap';

const FunctionTable = ({ points, onChange, currentPage, pointsPerPage }) => {
    const startIndex = currentPage * pointsPerPage;
    const endIndex = startIndex + pointsPerPage;
    const currentPoints = points.slice(startIndex, endIndex);

    return (
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>#</th>
                    <th>X</th>
                    <th>Y</th>
                </tr>
            </thead>
            <tbody>
                {currentPoints.map((point, index) => (
                    <tr key={startIndex + index}>
                        <td>{startIndex + index + 1}</td>
                        <td>
                            <Form.Control
                                type="number"
                                value={point.x}
                                onChange={(e) => onChange(startIndex + index, 'x', e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <Form.Control
                                type="number"
                                value={point.y}
                                onChange={(e) => onChange(startIndex + index, 'y', e.target.value)}
                                required
                            />
                        </td>
                    </tr>
                ))}
            </tbody>
        </Table>
    );
};

export default FunctionTable;
