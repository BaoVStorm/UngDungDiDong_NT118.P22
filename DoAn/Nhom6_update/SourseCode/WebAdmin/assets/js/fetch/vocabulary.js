let serverUrl = getUrlServer();

$(document).ready(function(){  
  loadDataLevel("api/vocabulary/getListVocabLevels", $(".selectLevel"));
  loadDataTopic("api/vocabulary/getListVocabTopics", $(".selectTopic"));

  filterTable(1, 1);

  $("#filterTopic").on("change", function() {
    filterTable($("#filterTopic").val(), $("#filterLevel").val());
  });

  $("#filterLevel").on("change", function() {
    filterTable($("#filterTopic").val(), $("#filterLevel").val());
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

    // console.log(data);
    // $selection.text(data);
  } catch (error) {
    console.error('Lỗi:', error);
  }
}


// showToast("successToast", "Dữ liệu đã được lưu thành công!", "Thành công", "check_circle");

// showToast("infoToast", "Hệ thống đang xử lý yêu cầu của bạn...", "Đang xử lý", "info");

// showToast("warningToast", "Một số trường chưa được điền đầy đủ.", "Cảnh báo", "warning");

// showToast("dangerToast", "Đã xảy ra lỗi khi lưu dữ liệu!", "Lỗi hệ thống", "error");

// --------------------------------------------- add topic
document.getElementById("addTopicForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const topicName = document.getElementById("inputTopicName").value.trim();
  if (!topicName) {
    // alert("Vui lòng nhập tên chủ đề.");
    showToast("warningToast", "Tên chủ đề không được trống!", "Cảnh Báo", "warning");
    return;
  }

  try {
    const res = await fetch(serverUrl + "api/vocabulary/addVocabTopic", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ topic_name: topicName })
    });

    const data = await res.json();

    if (res.ok) {
      showToast("successToast", `Thêm chủ đề "${topicName}" thành công`, "Thành công", "check_circle");
      document.getElementById("inputTopicName").value = "";

      addAdminLog("add", "Vocabulary", null, `Thêm mới Chủ đề "${topicName}"`, "Trần Vũ Bão", null);

    } else {
      showToast("dangerToast", "Lỗi: " + data.msg, "Lỗi", "error");
    }
  } catch (err) {
    showToast("dangerToast", "Lỗi kết nối máy chủ.", "Lỗi", "error");
    // console.error(err);
  }
});

// --------------------------------------------- add level
document.getElementById("addLevelForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const levelName = document.getElementById("inputLevelName").value.trim();
  if (!levelName) {
    showToast("warningToast", "Tên Mức độ không được trống!", "Cảnh Báo", "warning");
    return;
  }

  try {
    const res = await fetch(serverUrl + "api/vocabulary/addVocabLevel", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ level_name: levelName })
    });

    const data = await res.json();

    if (res.ok) {
      showToast("successToast", `Thêm Mức độ "${levelName}" thành công`, "Thành công", "check_circle");
      document.getElementById("inputLevelName").value = "";

      addAdminLog("add", "Vocabulary", null, `Thêm mới Mức độ "${levelName}"`, "Trần Vũ Bão", null);

    } else {
      showToast("dangerToast", "Lỗi: " + data.msg, "Lỗi", "error");
    }
  } catch (err) {
    showToast("dangerToast", "Lỗi kết nối máy chủ.", "Lỗi", "error");
    // console.error(err);
  }
});

// --------------------------------------------- add Vocabulary
document.getElementById("addVocabularyForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const wordName = document.getElementById("wordName").value.trim();
  const wordType = document.getElementById("wordType").value.trim();
  const wordPronunc = document.getElementById("wordPronunc").value.trim();
  const wordVN_meaning = document.getElementById("wordVN_meaning").value.trim();
  const wordE_meaning = document.getElementById("wordE_meaning").value.trim();
  const wordVN_example = document.getElementById("wordVN_example").value.trim();
  const wordE_example = document.getElementById("wordE_example").value.trim();
  const wordAudioURL = document.getElementById("wordAudioURL").value.trim();
  const wordImageURL = document.getElementById("wordImageURL").value.trim();
  const topicId = document.getElementById("selectTopic").value;
  const levelId = document.getElementById("selectLevel").value;

  if (!wordName || !wordType || !topicId || !levelId) {
    showToast("warningToast", "Vui lòng nhập đầy đủ các trường bắt buộc!", "Cảnh Báo", "warning");
    return;
  }

  try {
    const res = await fetch(serverUrl + "api/vocabulary/addVocabulary", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        word: wordName,
        type: wordType,
        pronunciation: wordPronunc,
        meaning_vietnamese: wordVN_meaning,
        meaning_english: wordE_meaning,
        example_vietnamese: wordVN_example,
        example_english: wordE_example,
        audio_url: wordAudioURL,
        image_url: wordImageURL,
        topic_id: Number(topicId),
        level_id: Number(levelId)
      })
    });

    const data = await res.json();

    if (res.ok) {
      showToast("successToast", `Thêm từ "${wordName}" thành công`, "Thành công", "check_circle");

      // Reset form nếu cần
      document.getElementById("addVocabularyForm").reset();

      // Ghi log admin
      addAdminLog(
        "add",
        "Vocabulary",
        data.vocab?._id || null,
        `Thêm từ mới "${wordName}"`,
        "Trần Vũ Bão", // hoặc dynamic từ session admin
        null
      );
    } else {
      showToast("dangerToast", "Lỗi: " + data.msg, "Lỗi", "error");
    }
  } catch (err) {
    showToast("dangerToast", "Lỗi kết nối máy chủ.", "Lỗi", "error");
    console.error(err);
  }
});

// ------------------------ filter

async function filterTable(topic_id, level_id) {
  try {
    const response = await fetch(serverUrl + `api/vocabulary/getVocabularyByTopicAndLevel?topic_id=${topic_id}&level_id=${level_id}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    let data = await response.json();

    data = data["vocabularies"];

    $block = $("#tableVocabularies");
    $block.html("");

    STT = 0;

    for (const row of data) {
      STT++;
      
      $block.append(`
        <tr id="${row["_id"]}">
          <td class="align-middle text-center text-sm">
            <span class="text-xs font-weight-bold" style="margin-top: 3px;">${STT}</span>
          </td>
          <td>
            <div class="d-flex px-2 py-1 ">
              <div class="d-flex flex-column justify-content-center ">
                <h6 class="mb-0 text-sm ">${row["word"]}</h6>
              </div>
            </div>
          </td>
          <td class="align-middle text-sm">
            <span class="text-xs font-weight-bold">${row["meaning_vietnamese"]}</span>
          </td>
          <td class="align-middle ">
            <span class="text-xs font-weight-bold">${row["meaning_english"]}</span>
          </td>
          <td class="align-middle text-center">
            <span class="text-xs font-weight-bold">${row["type"]}</span>
          </td>
          <td class="align-middle text-center">
            <span class="text-xs font-weight-bold">${row["pronunciation"]}</span>
          </td>    

          <td class="align-middle text-center">
            <a class="btnViewDetail" data-id="${row["_id"]}">
              <i class="fa fa-ellipsis-v text-secondary"></i>
            </a>
          </td>

        </tr>        
      `);
    }

    // Gắn sự kiện sau khi bảng được render
    $(".btnViewDetail").on("click", function () {
      console.log('asdfasdf')

      const vocabId = $(this).data("id");
      localStorage.setItem("selectedVocabId", vocabId);
      window.location.href = "detailVocabulary.html";
    });

    // console.log(data);
    // $selection.text(data);
  } catch (error) {
    showToast("dangerToast", "Lỗi: " + error, "Lỗi", "error");
    // console.error('Lỗi:', error);
  }
}

