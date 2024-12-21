import React from 'react';
import { Modal, Button } from 'react-bootstrap';

const CustomModal = ({
    show,
    onHide,
    title,
    body,
    confirmText = "ОК",
    cancelText,
    onConfirm,
    variant = "primary",
}) => {
    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>{title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>{body}</Modal.Body>
            <Modal.Footer>
                {cancelText && (
                    <Button variant="secondary" onClick={onHide}>
                        {cancelText}
                    </Button>
                )}
                <Button variant={variant} onClick={onConfirm || onHide}>
                    {confirmText}
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default CustomModal;
