import React, { useState } from 'react';

const TransactionsForm: React.FC = () => {
    const [userId, setUserId] = useState<string>(''); 
    const [date, setDate] = useState<string>(''); 
    const [amount, setAmount] = useState<number | ''>('');
    const [category, setCategory] = useState<string>(''); 
    const [type, setType] = useState<string>(''); 

    const handleTransactionSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        const transaction = {
            date,
            amount,
            category,
            type,
        };

        try {
            const response = await fetch(`http://localhost:8080/api/transactions?userId=${userId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(transaction),
            });

            if (response.ok) {
                alert('Transaction submitted successfully!');
            } else {
                alert('Failed to submit transaction');
            }
        } catch (error) {
            console.error('Error submitting transaction:', error);
            alert('An error occurred');
        }
    };

    return (
        <div>
            <h1>Submit a Transaction</h1>
            <form onSubmit={handleTransactionSubmit}>
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
};

export default TransactionsForm;
