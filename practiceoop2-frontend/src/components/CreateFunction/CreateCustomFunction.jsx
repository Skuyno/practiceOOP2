import React, { useState } from 'react';
import { Form, Button, Card} from 'react-bootstrap';
import api from '../../api';
import { create} from 'mathjs';
import { parse as mathParse } from 'mathjs';
import CustomModal from '../Common/CustomModal';

const limitedFunctions = {
  sin:  Math.sin,
  cos:  Math.cos,
  tan:  Math.tan,
  log:  Math.log,
  exp:  Math.exp,
  pow:  Math.pow,
  sqrt: Math.sqrt
};

const config = {};
const math = create({}, config);

math.import(limitedFunctions, { override: true });

math.parse = mathParse;

const CreateCustomFunction = () => {
  const [expression, setExpression] = useState('sin(x) + x^2');
  const [xFrom, setXFrom] = useState('');
  const [xTo, setXTo] = useState('');
  const [count, setCount] = useState('');
  const [functionName, setFunctionName] = useState('');

  const [error, setError] = useState(null);
  const [showErrorModal, setShowErrorModal] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [showSuccessModal, setShowSuccessModal] = useState(false);

  const handleCloseErrorModal = () => setShowErrorModal(false);
  const handleCloseSuccessModal = () => setShowSuccessModal(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const fromVal = parseFloat(xFrom);
      const toVal = parseFloat(xTo);
      const numPoints = parseInt(count, 10);

      if (isNaN(fromVal) || isNaN(toVal) || isNaN(numPoints) || numPoints <= 0) {
        throw new Error('Неверно заданы границы интервала или количество точек.');
      }
      if (!functionName.trim()) {
        throw new Error('Название функции не может быть пустым.');
      }
      if (toVal <= fromVal) {
        throw new Error('Правая граница должна быть больше левой!');
      }
      if (!expression.trim()) {
        throw new Error('Выражение функции не может быть пустым.');
      }

      const parsedExpr = math.parse(expression);

      const step = (toVal - fromVal) / (numPoints - 1);
      const points = [];
      for (let i = 0; i < numPoints; i++) {
        const xVal = fromVal + i * step;
        const scope = { x: xVal };
        const yVal = parsedExpr.evaluate(scope);  
        points.push({ x: xVal, y: yVal });
      }

      const mathFunctionData = {
        name: functionName,
        count: numPoints,
        x_from: fromVal,
        x_to: toVal,
      };

      const response = await api.post('/functions', mathFunctionData);
      const functionId = response.data.id;

      for (let i = 0; i < points.length; i++) {
        const pointData = {
          functionId,
          x: points[i].x,
          y: points[i].y,
        };
        await api.post('/points', pointData);
      }

      setSuccessMessage(
        `Функция "${functionName}" успешно создана на основе выражения "${expression}"!`
      );
      setShowSuccessModal(true);

      setExpression('sin(x) + x^2');
      setXFrom('');
      setXTo('');
      setCount('');
      setFunctionName('');
    } catch (err) {
      setError(err.message || 'Ошибка при создании функции');
      setShowErrorModal(true);
    }
  };

  return (
    <Card className="p-3">
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Название функции</Form.Label>
          <Form.Control
            type="text"
            placeholder="Например: sin(x) + x^2 на [0, 10]"
            value={functionName}
            onChange={(e) => setFunctionName(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Введите выражение</Form.Label>
          <Form.Control
            type="text"
            placeholder="sin(x) + x^2"
            value={expression}
            onChange={(e) => setExpression(e.target.value)}
            required
          />
          <Form.Text className="text-muted">
            Можно использовать только следующие функции: sin, cos, tan, log, exp, pow, sqrt.
            Пример: <code>pow(x, 2) + sin(x)</code>
          </Form.Text>
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Левая граница интервала (x_from)</Form.Label>
          <Form.Control
            type="number"
            placeholder="Например, 0"
            value={xFrom}
            onChange={(e) => setXFrom(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Правая граница интервала (x_to)</Form.Label>
          <Form.Control
            type="number"
            placeholder="Например, 10"
            value={xTo}
            onChange={(e) => setXTo(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Количество точек</Form.Label>
          <Form.Control
            type="number"
            placeholder="Например, 50"
            value={count}
            onChange={(e) => setCount(e.target.value)}
            required
            min="2"
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Создать
        </Button>
      </Form>

      <CustomModal
        show={showErrorModal}
        onHide={handleCloseErrorModal}
        title="Ошибка"
        body={error}
        confirmText="Закрыть"
        variant="danger"
      />

      <CustomModal
        show={showSuccessModal}
        onHide={handleCloseSuccessModal}
        title="Успех"
        body={successMessage}
        confirmText="OK"
        variant="success"
      />
    </Card>
  );
};

export default CreateCustomFunction;
