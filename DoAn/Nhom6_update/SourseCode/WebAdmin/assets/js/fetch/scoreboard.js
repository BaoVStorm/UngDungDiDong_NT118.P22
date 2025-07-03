let serverUrl = getUrlServer();

$(document).ready(function(){
    fetchTable("api/auth/getTopScoreByScore?number=15", $("#scoreboard"));
}); 

async function fetchTable(urlAPI, $chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    // console.log(data);

    $chart.html("");
    let STT = 0;

    for(i in data) {
        row = data[i];

        STT++;

        $chart.append(`
            <tr>
              <td >
                <span class="text-secondary text-xs font-weight-bold" style="margin-left:35px">${STT}</span>
              </td>
              <td>
                <div class="d-flex px-2 py-1">
                  <div>
                    <img src="../assets/img/avatar.png" class="avatar avatar-sm me-3 border-radius-lg" alt="user1">
                  </div>
                  <div class="d-flex flex-column justify-content-center">
                    <h6 class="mb-0 text-sm">${row["full_name"]}</h6>
                    <p class="text-xs text-secondary mb-0">${row["email"]}</p>
                  </div>
                </div>
              </td>
              <td class="align-middle text-center">
                <span class="text-secondary text-xs font-weight-bold">${row["score"]}</span>
              </td>
              <!-- <td class="align-middle text-center text-sm">
                <span class="badge badge-sm bg-gradient-success">Online</span>
              </td> -->

              <td class="align-middle">
                <!-- <a href="javascript:;" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                  Edit
                </a> -->
              </td>
            </tr>
        `);
    }

  } catch (error) {
    console.error('Lỗi:', error);
  }
}
