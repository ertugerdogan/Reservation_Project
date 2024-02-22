import React, { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';

const DaySchedule = () => {
    const days = ['Pzt', 'Salı', 'Çarş', 'Perş', 'Cuma'];
    const hours = Array.from({ length: 11 }, (_, i) => `${8 + i}`);
    const [selectedCell, setSelectedCell] = useState(null);

    const [tableData, setTableData] = useState(
        Array.from({ length: 5 }, () => Array.from({ length: 11 }, () => false))
    );

    const handleCellPress = (rowIndex, colIndex) => {
        setSelectedCell({ rowIndex, colIndex });
    };

    const handleMarkButtonPress = () => {
        if (selectedCell !== null) {
            const { rowIndex, colIndex } = selectedCell;
            const newTableData = [...tableData];
            newTableData[rowIndex][colIndex] = !newTableData[rowIndex][colIndex];
            setTableData(newTableData);
            setSelectedCell(null);
        }
    };

 
    return (
        <View style={styles.container}>
            <View style={styles.timeColumn}>{renderTable()}</View>
            <TouchableOpacity style={styles.markButton} onPress={handleMarkButtonPress}>
                <Text style={styles.markButtonText}>İşaretle</Text>
            </TouchableOpacity>


        </View>
    );

    function renderTable() {
        return (
            <>
                <View style={styles.headerRow}>
                    <View style={styles.headerTextContainer}></View>
                    {hours.map((hour, index) => (
                        <View key={index} style={styles.headerTextContainer}>
                            <Text style={styles.headerText}>{hour}</Text>
                        </View>
                    ))}
                </View>
                {tableData.map((rowData, rowIndex) => (
                    <View key={rowIndex} style={styles.row}>
                        <View style={styles.headerTextContainer}>
                            <Text style={styles.headerText}>{days[rowIndex]}</Text>
                        </View>
                        {rowData.map((isMarked, colIndex) => (
                            <TouchableOpacity
                                key={colIndex}
                                style={[styles.cell, isMarked && styles.markedCell]}
                                onPress={() => handleCellPress(rowIndex, colIndex)}
                            ></TouchableOpacity>
                        ))}
                    </View>
                ))}
            </>
        );
    }
};

const styles = StyleSheet.create({
    container: { flex: 1, padding: 16, paddingTop: 30, backgroundColor: '#fff' },
    headerRow: { flexDirection: 'row', justifyContent: 'space-between', marginBottom: 10 },
    row: { flexDirection: 'row', alignItems: 'center', marginBottom: 10 },
    cell: {
        flex: 1,
        borderWidth: 1,
        borderColor: '#c8e1ff',
        height: 40,
    },
    markedCell: { backgroundColor: 'red' },
    emptyCell: { width: 60, marginRight: 5, textAlign: 'center' },
    headerTextContainer: { flex: 1, justifyContent: 'flex-end' },
    headerText: { textAlign: 'center', fontWeight: 'bold' },
    timeColumn: { flexDirection: 'column', marginRight: 5 },
    markButton: {
        backgroundColor: 'blue',
        padding: 10,
        alignItems: 'center',
        marginTop: 20,
        borderRadius: 5,
    },
    markButtonText: { color: 'white', fontWeight: 'bold' },
    timeCell: { flex: 1, alignItems: 'center', justifyContent: 'flex-end' },
    timeText: { fontWeight: 'bold' },

    saveButton: {
        backgroundColor: 'green',
        padding: 10,
        alignItems: 'center',
        marginTop: 20,
        borderRadius: 5,
    },
    saveButtonText: { color: 'white', fontWeight: 'bold' },
});

export default DaySchedule;
