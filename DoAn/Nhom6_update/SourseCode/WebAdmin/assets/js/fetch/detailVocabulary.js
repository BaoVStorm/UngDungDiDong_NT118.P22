let serverUrl = getUrlServer();

let vocabId = localStorage.getItem("selectedVocabId");

$(document).ready(function(){  
  
  // localStorage.removeItem("selectedVocabId");
  
  console.log(vocabId);
  
  loadDataLevel("api/vocabulary/getListVocabLevels", $(".selectLevel"));
  // loadDataTopic("api/vocabulary/getListVocabTopics", $(".selectTopic"));

  // getDetailWord(vocabId);

  $("#outPage").on("click", async () => {
    window.history.back()
  });

}); 

async function loadDataLevel(urlAPI, $selection) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    let data = await response.json();

    data = data["vocabLevels"];

    $selection.each(function () {
      const select = $(this);
      select.html(""); // clear option

      select.append(`<option value="" disabled selected hidden>-- Chọn mức độ --</option>`);

      for (const row of data) {
        select.append(`<option value="${row["level_id"]}">${row["level_name"]}</option>`);
      }
    });

    $("#filterLevel").val("1");

    loadDataTopic("api/vocabulary/getListVocabTopics", $(".selectTopic"));

    // console.log(data);
    // $selection.text(data);
  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function loadDataTopic(urlAPI, $selection) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    let data = await response.json();
    data = data["vocabTopics"];

    $selection.each(function () {
      const select = $(this);
      select.html(""); // clear option

      select.append(`<option value="" disabled selected hidden>-- Chọn chủ đề --</option>`);

      for (const row of data) {
        select.append(`<option value="${row["topic_id"]}">${row["topic_name"]}</option>`);
      }
    });

    $("#filterTopic").val("1");

    getDetailWord(vocabId);

    // console.log(data);
    // $selection.text(data);
  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function getDetailWord(vocabId) {
  try {
    const response = await fetch(serverUrl + "api/vocabulary/getDetailWord", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ word_id: vocabId })
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    let data = await response.json();

    editForm(data)

    console.log(data);
    // $selection.text(data);
  } catch (error) {
    showToast("dangerToast", "Lỗi: " + error, "Lỗi", "error");
    // console.error('Lỗi:', error);
  }
}

function editForm(data) {
  // data = await response.json();

  // $("#word")
  // $("#type")
  // $("#pronunciation")
  // $("#meaning_vietnamese")
  // $("#meaning_english")
  // $("#example_vietnamese")
  // $("#example_english")
  // $("#image_url")
  // $("#audio_url")
  // $("#topic_id") selection
  // $("#level_id") selection


  // Trường input và textarea
  $("#word").val(data.word).attr("placeholder", data.word);
  $("#type").val(data.type).attr("placeholder", data.type);
  $("#pronunciation").val(data.pronunciation).attr("placeholder", data.pronunciation);
  $("#meaning_vietnamese").val(data.meaning_vietnamese).attr("placeholder", data.meaning_vietnamese);
  $("#meaning_english").val(data.meaning_english).attr("placeholder", data.meaning_english);
  $("#example_vietnamese").val(data.example_vietnamese).attr("placeholder", data.example_vietnamese);
  $("#example_english").val(data.example_english).attr("placeholder", data.example_english);
  $("#image_url").val(data.image_url).attr("placeholder", data.image_url);
  $("#audio_url").val(data.audio_url).attr("placeholder", data.audio_url);

  // Select topic_id
  $("#topic_id").val(data.topic_id);

  // Select level_id
  $("#level_id").val(data.level_id);

}

// ----------------------------

document.addEventListener("DOMContentLoaded", () => {
    // Disable nút cập nhật ban đầu
    $("#btnUpdateVocab").prop("disabled", true);

    // Bắt sự kiện thay đổi bất kỳ input nào để kích hoạt nút cập nhật
    $("#updateVocabForm input, #updateVocabForm textarea, #updateVocabForm select").on("input change", function () {
      $("#btnUpdateVocab").prop("disabled", false);
    });

    // Sự kiện cập nhật
    $("#btnUpdateVocab").on("click", async () => {
      const vocabData = {
        vocab_id: vocabId,
        word: $("#word").val().trim(),
        type: $("#type").val().trim(),
        pronunciation: $("#pronunciation").val().trim(),
        meaning_vietnamese: $("#meaning_vietnamese").val().trim(),
        meaning_english: $("#meaning_english").val().trim(),
        example_vietnamese: $("#example_vietnamese").val().trim(),
        example_english: $("#example_english").val().trim(),
        image_url: $("#image_url").val().trim(),
        audio_url: $("#audio_url").val().trim(),
        topic_id: parseInt($("#topic_id").val()),
        level_id: parseInt($("#level_id").val())
      };

      try {
        const response = await fetch(serverUrl + "api/vocabulary/updateVocabulary", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(vocabData)
        });

        const data = await response.json();
        if (response.ok) {
          showToast("successToast", "Cập nhật từ vựng thành công", "Thành công", "check_circle");
          $("#btnUpdateVocab").prop("disabled", true);

          addAdminLog("edit", "Vocabulary", null, `Cập nhật Từ "${$("#word").val().trim()}"`, "Trần Vũ Bão", null);

        } else {
          showToast("dangerToast", "Lỗi: " + data.msg, "Lỗi", "error");
        }
      } catch (err) {
        showToast("dangerToast", "Lỗi kết nối máy chủ.", "Lỗi", "error");
      }

    });

    // Sự kiện xoá
    $("#btnDeleteVocab").on("click", async () => {
      if (!confirm("Bạn có chắc chắn muốn xoá từ này?")) return;

      try {
        const response = await fetch(serverUrl + "api/vocabulary/deleteVocabulary", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({vocab_id: vocabId})
        });

        const data = await response.json();
        if (response.ok) {
          addAdminLog("delete", "Vocabulary", null, `Xoá Từ "${$("#word").val().trim()}"`, "Trần Vũ Bão", null);

          showToast("successToast", "Xoá từ thành công", "Thành công", "check_circle");
          setTimeout(() => window.history.back(), 1000); // quay lại trang trước
        } else {
          showToast("dangerToast", "Lỗi: " + data.msg, "Lỗi", "error");
        }
      } catch (err) {
        showToast("dangerToast", "Lỗi kết nối máy chủ.", "Lỗi", "error");
      }

    });
  });