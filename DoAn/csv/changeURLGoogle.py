import pandas as pd
import re

def convert_gdrive_url(url):
    if isinstance(url, str) and url.startswith("https://drive.google.com"):
        match = re.search(r'/d/([a-zA-Z0-9_-]+)', url)
        if match:
            file_id = match.group(1)
            return f'https://drive.google.com/uc?export=view&id={file_id}'
    return url  # Trả lại URL gốc nếu không phải link Drive hợp lệ

# Đọc file CSV
df = pd.read_csv('MiniTest8.csv')  # Thay bằng tên file thật

# Danh sách các cột cần xử lý
image_columns = ['url_image1', 'url_image2', 'url_image3', 'url_image4', 'url_image5']

# Áp dụng chuyển đổi trên từng cột
for col in image_columns:
    if col in df.columns:
        df[col] = df[col].apply(convert_gdrive_url)

# Lưu lại kết quả nếu cần
df.to_csv('MiniTest8_done.csv', index=False)

print("✅ Đã chuyển đổi các link Google Drive thành công.")
