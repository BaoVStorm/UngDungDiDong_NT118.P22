function getUrlServer() {
    return "http://18.140.5.206:3000/"
    // return
    // return
}

// showToast("successToast", "Dữ liệu đã được lưu thành công!", "Thành công", "check_circle");

// showToast("infoToast", "Hệ thống đang xử lý yêu cầu của bạn...", "Đang xử lý", "info");

// showToast("warningToast", "Một số trường chưa được điền đầy đủ.", "Cảnh báo", "warning");

// showToast("dangerToast", "Đã xảy ra lỗi khi lưu dữ liệu!", "Lỗi hệ thống", "error");

async function addAdminLog(actionType, targetType, targetId, description, adminName, adminID = null) {
  try {
    const res = await fetch(serverUrl + "api/admin/addAdminLog", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        adminID: adminID,
        admin_name: adminName,
        action_type: actionType,           // "add", "edit", "delete", "login"
        target_type: targetType,           // "vocab", "topic", "test",...
        target_id: targetId,               // ID dạng string, ví dụ: "43"
        description: description           // Mô tả hành động
      })
    });

    const data = await res.json();

    if (res.ok) {
      console.log("Ghi log admin thành công:", data.msg);
    } else {
      console.error("Lỗi khi ghi log:", data.msg);
    }
  } catch (err) {
    console.error("Lỗi kết nối khi gửi log:", err);
  }
}
