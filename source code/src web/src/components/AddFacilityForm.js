import React from 'react';

const AddFacilityForm = ({ newFacilityData, onChange, onSave, onCancel }) => {
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    onChange({ ...newFacilityData, [name]: value });
  };

  const handleSave = () => {
    onSave();
  };

  const handleCancel = () => {
    onCancel();
  };

  return (
    <div className="modal">
      <h3>Add New Facility</h3>
      <form>
        <label>Name:</label>
        <input
          type="text"
          name="name"
          value={newFacilityData.name}
          onChange={handleInputChange}
        />
        <label>Type:</label>
        <select
          name="type"
          value={newFacilityData.type}
          onChange={handleInputChange}
        >
          <option value="Spor Tesisi">Spor Tesisi</option>
        </select>
        <label>Capacity:</label>
        <input
          type="number"
          name="capacity"
          value={newFacilityData.capacity}
          onChange={handleInputChange}
        />
        <label>Available Spots:</label>
        <input
          type="number"
          name="available_spots"
          value={newFacilityData.available_spots}
          onChange={handleInputChange}
        />
        <button type="button" onClick={handleSave}>Save</button>
        <button type="button" onClick={handleCancel}>Cancel</button>
      </form>
    </div>
  );
};

export default AddFacilityForm;
