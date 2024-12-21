import React from 'react';
import { Table, Form } from 'react-bootstrap';

const FunctionTable = ({ points, onChange }) => {
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
                {points.map((point, index) => (
                    <tr key={index}>
                        <td>{index + 1}</td>
                        <td>
                            <Form.Control
                                type="number"
                                value={point.x}
                                onChange={(e) => onChange(index, 'x', e.target.value)}
                                required
                            />
                        </td>
                        <td>
                            <Form.Control
                                type="number"
                                value={point.y}
                                onChange={(e) => onChange(index, 'y', e.target.value)}
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