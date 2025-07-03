let serverUrl = getUrlServer();

$(document).ready(function(){
    setUserCount();
    setTranslateCount();
    fetchData("api/look_up_history/getLookUpCount", $("#SoTuDaTra"));
    fetchData("api/user_star/getUserStarCount", $("#TuDaDanhDau"));

    fetchChartByWeek("api/certificate/getCertificateDoneByWeek", testChart);

    fetchLineChartByWeek("api/look_up_history/getLookupDoneByWeek", translateChart);
    fetchLineChartByWeek("api/user_star/getUserStarCountByWeek", markChart);

    fetchTable("api/certificate/getTop10LastestCertificates?limit=15", $("#ListTestTable"));

    // 
    fetchAdminActivity("api/admin/getAdminLogs?limit=8", $("#historyAdmin"));
}); 

async function fetchAdminActivity(urlAPI, $object) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();
    $object.html("");

    for (const row of data) {
      // Chọn icon tương ứng theo action_type
      let iconName = "info"; // fallback mặc định
      let iconColor = "text-dark";

      switch (row.action_type?.toLowerCase()) {
        case "add":
          iconName = "add_circle";
          iconColor = "text-success";
          break;
        case "edit":
          iconName = "edit";
          iconColor = "text-warning";
          break;
        case "delete":
          iconName = "delete";
          iconColor = "text-danger";
          break;
        case "login":
          iconName = "login";
          iconColor = "text-primary";
          break;
      }

      $object.append(`
        <div class="timeline-block mb-3">
          <span class="timeline-step">
            <i class="material-symbols-rounded ${iconColor} text-gradient">${iconName}</i>
          </span>
          <div class="timeline-content" style="margin-left:50px">
            <h6 class="text-dark text-sm font-weight-bold mb-0">${row.action_type.toUpperCase()}. ${row.description}</h6>
            <p class="text-secondary font-weight-bold text-xs mt-1 mb-0">${row.target_type} - ${row.created_at}</p>
            <p class="text-xs font-weight-bold mb-0 mt-2">By ${row.admin_name}</p>
          </div>
        </div>
      `);
    }

  } catch (error) {
    console.error('Lỗi:', error);
  }
}


async function setUserCount() {
  try {
    const response = await fetch(serverUrl + "api/auth/getUserCount");
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // console.log(data);
    $("#TongSoHocVien").text(data);
  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function setTranslateCount() {
  try {
    const response = await fetch(serverUrl + "api/user_answers/getCorrectPercent");
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // console.log(data);
    $("#TyLeLamDung").text(data + "%");
  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function fetchData(urlAPI, $object) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // console.log(data);
    $object.text(data);
  } catch (error) {
    console.error('Lỗi:', error);
  }
}

    // setChartData(testChart, ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"], [10, 20, 5, 12, 18, 30, 22]);

async function fetchChartByWeek(urlAPI, chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // console.log(data);
    // $object.text(data);
    setChartData(chart, ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"], data);
  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function fetchLineChartByWeek(urlAPI, chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    const months = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
    // const testCounts = [15, 25, 10, 45, 30, 20, 50, 60, 30, 20, 40, 55];

    setLineChartData(chart, months, data);

  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function fetchLineChartByMonth(urlAPI, chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    const testCounts = [15, 25, 10, 45, 30, 20, 50, 60, 30, 20, 40, 55];

    setLineChartData(chart, months, testCounts);

  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function fetchTable(urlAPI, $chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    // console.log(data);

    $chart.html("");

    for(i in data) {
        row = data[i];
        // console.log(row["certificate_name"]);

        $chart.append(`
            <tr>
                <td>
                <div class="d-flex px-2 py-1">
                    <h6 class="mb-0 text-sm" style="margin-left:25px">${row["certificate_name"]}</h6>
                </div>
                </td>
                <td>
                    <div style="display: flex; align-items: center; gap: 10px; margin-left:35px">
                        <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="tranvubao2004">
                            <img src="../assets/img/avatar.png" alt="team1">
                        </a>

                        <span class="text-xs font-weight-bold" style="margin-top: 3px;">${row["email"]}</span>
                    </div>
                </td>
                <td class="align-middle text-center text-sm">
                <span class="text-xs font-weight-bold"> ${row["awarded_at"]} </span>
                </td>
                <td class="align-middle">
                <div class="progress-wrapper w-75 mx-auto">
                    <div class="progress-info">
                    <div class="progress-percentage">
                        <span class="text-xs font-weight-bold">${row["completion_rate"]}%</span>
                    </div>
                    </div>
                    <div class="progress">
                    <div class="progress-bar bg-gradient-info" role="progressbar" aria-valuenow="${row["completion_rate"]}" aria-valuemin="0" aria-valuemax="100" style="width:${row["completion_rate"]}%"></div>
                    </div>
                </div>
                </td>
            </tr>
        `);
    }

  } catch (error) {
    console.error('Lỗi:', error);
  }
}