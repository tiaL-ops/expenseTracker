import React, { useState } from 'react';

function App() {
    const [userId, setUserId] = useState(''); 
    const [date, setDate] = useState(''); 
    const [amount, setAmount] = useState<number | ''>('');
    const [category, setCategory] = useState(''); 
    const [type, setType] = useState(''); 

    const handleTransactionSubmit = async () => {
        console.log("Form submitted");
    };

    return (
        <div>
            <h1>Submit a Transaction</h1>
            <form onSubmit={(e) => { e.preventDefault(); handleTransactionSubmit(); }}>
                <label>
                    User ID:
                    <input type="text" value={userId} onChange={(e) => setUserId(e.target.value)} />
                </label>
                <br />
                <label>
                    Date:
                    <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
                </label>
                <br />
                <label>
                    Amount:
                    <input type="number" value={amount} onChange={(e) => setAmount(Number(e.target.value))} />
                </label>
                <br />
                <label>
                    Category:
                    <input type="text" value={category} onChange={(e) => setCategory(e.target.value)} />
                </label>
                <br />
                <label>
                    Type:
                    <select value={type} onChange={(e) => setType(e.target.value)}>
                        <option value="">Select type</option>
                        <option value="income">Income</option>
                        <option value="expense">Expense</option>
                    </select>
                </label>
                <br />
                <button type="submit">Submit Transaction</button>
            </form>
        </div>
    );
}

export default App;
