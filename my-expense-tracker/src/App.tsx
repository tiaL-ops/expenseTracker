import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginForm from './component/LoginForm';
import TransactionsForm from './component/TransactionForm'; 

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginForm />} />
                <Route path="/transactions" element={<TransactionsForm />} />
            </Routes>
        </Router>
    );
}

export default App;
