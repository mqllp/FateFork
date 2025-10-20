// 游戏相关JavaScript功能

// 页面加载完成后的初始化
document.addEventListener('DOMContentLoaded', function() {
    // 添加页面加载动画
    addLoadingAnimation();
    
    // 初始化游戏功能
    initGameFeatures();
});

// 添加加载动画
function addLoadingAnimation() {
    const containers = document.querySelectorAll('.game-container, .ending-container, .leaderboard-container');
    containers.forEach(container => {
        container.classList.add('fade-in-up');
    });
}

// 初始化游戏功能
function initGameFeatures() {
    // 为选择按钮添加点击效果
    const choiceButtons = document.querySelectorAll('.choice-btn');
    choiceButtons.forEach(button => {
        button.addEventListener('click', function() {
            // 添加点击动画
            this.style.transform = 'scale(0.95)';
            setTimeout(() => {
                this.style.transform = '';
            }, 150);
        });
    });
    
    // 为分享按钮添加功能
    initShareFeatures();
}

// 初始化分享功能
function initShareFeatures() {
    // 复制到剪贴板功能
    window.copyToClipboard = function() {
        const endingType = document.querySelector('[th\\:text="${endingNode.endingType}"]')?.textContent || '未知结局';
        const finalScore = document.querySelector('[th\\:text="${finalScore}"]')?.textContent || '0';
        const text = `我在《命运分岔口》游戏中获得了"${endingType}"结局，最终分数：${finalScore}分！`;
        
        if (navigator.clipboard) {
            navigator.clipboard.writeText(text).then(() => {
                showToast('分享文本已复制到剪贴板！', 'success');
            }).catch(() => {
                fallbackCopyTextToClipboard(text);
            });
        } else {
            fallbackCopyTextToClipboard(text);
        }
    };
    
    // 分享到微博功能
    window.shareToWeibo = function() {
        const endingType = document.querySelector('[th\\:text="${endingNode.endingType}"]')?.textContent || '未知结局';
        const finalScore = document.querySelector('[th\\:text="${finalScore}"]')?.textContent || '0';
        const text = `我在《命运分岔口》游戏中获得了"${endingType}"结局，最终分数：${finalScore}分！`;
        const url = `https://service.weibo.com/share/share.php?url=${encodeURIComponent(window.location.origin)}&title=${encodeURIComponent(text)}`;
        window.open(url, '_blank');
    };
}

// 备用复制方法
function fallbackCopyTextToClipboard(text) {
    const textArea = document.createElement('textarea');
    textArea.value = text;
    textArea.style.position = 'fixed';
    textArea.style.left = '-999999px';
    textArea.style.top = '-999999px';
    document.body.appendChild(textArea);
    textArea.focus();
    textArea.select();
    
    try {
        const successful = document.execCommand('copy');
        if (successful) {
            showToast('分享文本已复制到剪贴板！', 'success');
        } else {
            showToast('复制失败，请手动复制', 'error');
        }
    } catch (err) {
        showToast('复制失败，请手动复制', 'error');
    }
    
    document.body.removeChild(textArea);
}

// 显示提示消息
function showToast(message, type = 'info') {
    // 创建提示元素
    const toast = document.createElement('div');
    toast.className = `alert alert-${type === 'success' ? 'success' : type === 'error' ? 'danger' : 'info'} alert-dismissible fade show position-fixed`;
    toast.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    toast.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    document.body.appendChild(toast);
    
    // 3秒后自动移除
    setTimeout(() => {
        if (toast.parentNode) {
            toast.parentNode.removeChild(toast);
        }
    }, 3000);
}

// 游戏记录详情显示
function showRecordDetails(button) {
    const description = button.getAttribute('data-description');
    const modal = document.getElementById('recordModal');
    const modalBody = document.getElementById('recordDescription');
    
    if (modal && modalBody) {
        modalBody.innerHTML = description.replace(/\n/g, '<br>');
        const bsModal = new bootstrap.Modal(modal);
        bsModal.show();
    }
}

// 分享游戏记录
function shareRecord(button) {
    const ending = button.getAttribute('data-ending');
    const score = button.getAttribute('data-score');
    const text = `我在《命运分岔口》游戏中获得了"${ending}"结局，最终分数：${score}分！`;
    
    if (navigator.share) {
        navigator.share({
            title: '命运分岔口游戏记录',
            text: text,
            url: window.location.origin
        }).catch(err => {
            console.log('分享失败:', err);
            fallbackCopyTextToClipboard(text);
        });
    } else {
        fallbackCopyTextToClipboard(text);
    }
}

// 随机事件处理
function handleRandomEvent(choiceId) {
    // 这里可以添加随机事件的特殊处理逻辑
    console.log('处理随机事件:', choiceId);
}

// 游戏统计
function trackGameEvent(eventName, eventData) {
    // 这里可以添加游戏统计逻辑
    console.log('游戏事件:', eventName, eventData);
}
