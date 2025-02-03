"use client";

import { useState, useEffect } from "react";
import axios from "axios";
import { Toaster, toast } from "react-hot-toast";

export default function BookingPage() {
  const [sessions, setSessions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    customerName: "",
    email: "",
    phone: "",
    duration: "",
    availabilities: [{ startTime: "", endTime: "", sessionId: "" }],
  });

  useEffect(() => {
    fetchSessions();
  }, []);

  const fetchSessions = async () => {
    try {
      console.log(process.env.API_URL)
      const response = await axios.get(`http://localhost:8080/sessions`);
      console.log(response)
      setSessions(response.data);
    } catch (error) {
      console.error("Error fetching sessions:", error);
      toast.error("Failed to load sessions. Try again later.");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleAvailabilityChange = (index, field, value) => {
    const updatedAvailabilities = [...formData.availabilities];
    updatedAvailabilities[index][field] = value;
    setFormData({ ...formData, availabilities: updatedAvailabilities });
  };

  const addAvailability = () => {
    setFormData({
      ...formData,
      availabilities: [...formData.availabilities, { startTime: "", endTime: "", sessionId: "" }],
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    toast.loading("Submitting booking...");

    try {
      const response = await axios.post(`http://localhost:8080/bookings`, formData);
      toast.dismiss(); // Remove loading toast
      toast.success("Booking successful!");
      console.log(response.data);
    } catch (error) {
      toast.dismiss(); // Remove loading toast
      console.error("Booking error:", error);
      toast.error(error.response?.data?.message || "Failed to book session. Try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
      <div className="max-w-xl mx-auto p-6 bg-white rounded-lg shadow-md mt-10">
        <Toaster /> {/* Toast Notifications */}
        <h2 className="text-2xl font-bold mb-4">Book a Session</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input type="text" name="customerName" placeholder="Name" onChange={handleChange} className="w-full p-2 border rounded" required />
          <input type="email" name="email" placeholder="Email" onChange={handleChange} className="w-full p-2 border rounded" required />
          <input type="text" name="phone" placeholder="Phone" onChange={handleChange} className="w-full p-2 border rounded" required />
          <input type="number" name="duration" placeholder="Duration (in hours)" onChange={handleChange} className="w-full p-2 border rounded" required />

          {formData.availabilities.map((availability, index) => (
              <div key={index} className="space-y-2 border p-4 rounded">
                <select name="sessionId" onChange={(e) => handleAvailabilityChange(index, "sessionId", e.target.value)} className="w-full p-2 border rounded" required>
                  <option value="">Select Session</option>
                  {sessions.map((session) => (
                      <option key={session.id} value={session.sessionId}>{session.sessionType}</option>
                  ))}
                </select>
                <input type="datetime-local" name="startTime" onChange={(e) => handleAvailabilityChange(index, "startTime", e.target.value)} className="w-full p-2 border rounded" required />
                <input type="datetime-local" name="endTime" onChange={(e) => handleAvailabilityChange(index, "endTime", e.target.value)} className="w-full p-2 border rounded" required />
              </div>
          ))}

          <button type="button" onClick={addAvailability} className="w-full bg-blue-500 text-white p-2 rounded">Add Availability</button>
          <button type="submit" className="w-full bg-green-500 text-white p-2 rounded" disabled={loading}>
            {loading ? "Submitting..." : "Submit Booking"}
          </button>
        </form>
      </div>
  );
}
